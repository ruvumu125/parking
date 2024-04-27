package com.parking.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.parking.dto.*;
import com.parking.model.*;
import com.parking.repository.*;
import com.parking.validator.DepositValidator;
import com.parking.validator.PaymentValidator;
import org.springframework.stereotype.Service;

import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.services.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final PaymentRepository paymentRepository;
    private final DepositRepository depositRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehiculeAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(PaymentRepository paymentRepository, DepositRepository depositRepository, ParkingTicketRepository parkingTicketRepository, VehiculeAccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.depositRepository = depositRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public PaymentDto savePayment(PaymentDto dto) {

        List<String> errors = PaymentValidator.validate(dto);
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le paiement n'est pas valide", ErrorCodes.PAYMENT_NOT_FOUND,errors);
        }
        if(!isTicketExist(dto.getParkingTicket().getId())) {
            throw new EntityNotFoundException("Aucune ticket de parking avec l'ID = " +dto.getParkingTicket().getId()+ " n' a ete trouve dans la BDD",
                    ErrorCodes.PARKINGTICKET_NOT_FOUND);
        }

        if(isTicketPaid(dto.getParkingTicket().getId())) {
            throw new InvalidEntityException("Le ticket est deja paye.", ErrorCodes.PARKINGTICKET_ALREADY_PAID);
        }

        if(!isTicketClosed(dto.getParkingTicket().getId())) {
            throw new InvalidEntityException("Vehicule est deja en parking.", ErrorCodes.PARKINGTICKET_VEHICLE_ALREADY_IN_PARKING);
        }

        if(!isAccountExist(dto.getTransaction().getAccount().getId())) {
            throw new EntityNotFoundException("Aucun compte avec l'ID = " +dto.getTransaction().getAccount().getId()+ " n' a ete trouve dans la BDD",
                    ErrorCodes.ACCOUNT_NOT_FOUND);
        }
        BigDecimal accountSoldValue = transactionRepository.accountSold(dto.getTransaction().getAccount().getId());
        BigDecimal sold= BigDecimal.valueOf(0);
        sold = Objects.requireNonNullElseGet(accountSoldValue, () -> BigDecimal.valueOf(0));
        if (sold.compareTo(dto.getTransaction().getTransactionAmount())< 0){

            throw new InvalidEntityException("Solde insuffisant.", ErrorCodes.ACCOUNT_SOLD_NOT_ENOUGHT);
        }

        dto.getTransaction().setTransactionCode(transactionCodePrefix()+generateTransactionCode(10));
        dto.getTransaction().setTransactionDate(Instant.now());
        dto.getTransaction().setTransactionType(TransactionTypeEnum.PAYMENT);
        dto.getTransaction().setTransactionAmount(
                BigDecimal.valueOf(
                        Math.abs(dto.getTransaction().getTransactionAmount().doubleValue())* -1
                )
        );
        TransactionDto savedTransaction = TransactionDto.fromEntity(
                transactionRepository.save(TransactionDto.toEntity(dto.getTransaction()))
        );

        //update state
        Optional<ParkingTicket> existingParkingTicket = parkingTicketRepository.findById(dto.getParkingTicket().getId());
        ParkingTicket existingData = existingParkingTicket.get();
        existingData.setParkingTicketPaymentStatusEnum(ParkingTicketPaymentStatusEnum.PAID);
        parkingTicketRepository.save(existingData);

        PaymentDto paymentDto = fromTransaction(savedTransaction,dto.getParkingTicket());
        return PaymentDto.fromEntity(
                   paymentRepository.save(PaymentDto.toEntity(paymentDto))
        );
    }

    @Override
    public DepositDto saveDeposit(DepositDto dto) {

        List<String> errors = DepositValidator.validate(dto);
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le depot n'est pas valide", ErrorCodes.DEPOSIT_NOT_FOUND,errors);
        }

        dto.getTransaction().setTransactionCode(transactionCodePrefix()+generateTransactionCode(10));
        dto.getTransaction().setTransactionDate(Instant.now());
        dto.getTransaction().setTransactionType(TransactionTypeEnum.DEPOSIT);
        dto.getTransaction().setTransactionAmount(
                BigDecimal.valueOf(
                        Math.abs(dto.getTransaction().getTransactionAmount().doubleValue())
                )
        );
        TransactionDto savedTransaction = TransactionDto.fromEntity(
                transactionRepository.save(TransactionDto.toEntity(dto.getTransaction()))
        );

        DepositDto depositDto = fromTransaction(savedTransaction);
        return DepositDto.fromEntity(
                depositRepository.save(DepositDto.toEntity(depositDto))
        );
    }

    private PaymentDto fromTransaction(TransactionDto dto,ParkingTicketDto parkingTicketDto) {

        return PaymentDto.builder()
                .transaction(dto)
                .parkingTicket(parkingTicketDto)
                .build();
    }

    private DepositDto fromTransaction(TransactionDto dto) {

        return DepositDto.builder()
                .transaction(dto)
                .build();
    }

    private boolean isTicketExist(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findById(id);
        return parkingTicket.isPresent();
    }

    private boolean isTicketPaid(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findVehiclePaidTicketById(id);
        return parkingTicket.isPresent();
    }

    private boolean isTicketClosed(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findVehicleCloseTicketById(id);
        return parkingTicket.isPresent();
    }

    private boolean isAccountExist(Long id) {
        Optional<VehiculeAccount> account = accountRepository.findById(id);
        return account.isPresent();
    }

    public static String generateTransactionCode(int length) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    public static String transactionCodePrefix() {

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        return String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
    }
}
