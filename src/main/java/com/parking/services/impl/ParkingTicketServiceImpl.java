package com.parking.services.impl;

import com.parking.dto.*;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.model.*;
import com.parking.repository.ParkingPriceRepository;
import com.parking.repository.ParkingTicketRepository;
import com.parking.repository.VehicleRepository;
import com.parking.services.ParkingTicketService;
import com.parking.validator.ParkingTicketValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.time.Duration;

@Service
@Slf4j
public class ParkingTicketServiceImpl implements ParkingTicketService {

    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public ParkingTicketServiceImpl(ParkingTicketRepository parkingTicketRepository, ParkingPriceRepository parkingPriceRepository, VehicleRepository vehicleRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public ParkingTicketDto entryParking(ParkingTicketDto dto) {

        List<String> errors = ParkingTicketValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Parking ticket is not valid {}", dto);
            throw new InvalidEntityException("Le ticket de parking n'est pas valide", ErrorCodes.PARKINGTICKET_NOT_VALID, errors);
        }

        if(!isVehiculeRegistered(dto.getVehicle().getRegistrationNumber())) {
            log.error("Le vehicule n'est enregistré.", dto.getVehicle().getRegistrationNumber());
            throw new InvalidEntityException("Le vehicule n'est enregistré.");
        }

        if(isVehiculeHasDebt(dto.getVehicle().getRegistrationNumber())) {
            log.error("Le vehicule a une dette.", dto.getVehicle().getRegistrationNumber());
            throw new InvalidEntityException("Le vehicule a une dette.", ErrorCodes.VEHICLE_HAS_DEBT);
        }

        dto.setParkingTicketNumber(ticketNumberPrefix()+generateAccountNumber(8));
        dto.setEntryTime(Instant.now());
        dto.setParkingTicketStatusEnum(ParkingTicketStatusEnum.ACTIVE);
        dto.setParkingTicketPaymentStatusEnum(ParkingTicketPaymentStatusEnum.UNPAID);

        return ParkingTicketDto.fromEntity(
                parkingTicketRepository.save(ParkingTicketDto.toEntity(dto))
        );


    }

    private boolean isVehiculeRegistered(String plateNumber) {
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByRegistrationNumber(plateNumber);
        return vehicle.isPresent();
    }
    private boolean isVehiculeHasDebt(String plateNumber) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findVehicleUnpaidTicketByRegistrationNumber(plateNumber);
        return parkingTicket.isPresent();
    }

    public static String generateAccountNumber(int length) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    public static String ticketNumberPrefix() {

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        return String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
    }

    @Override
    @Transactional
    public ParkingTicketDto exitParking(Long parkingTicketId) {

        Optional<ParkingTicket> existingParkingTicket = parkingTicketRepository.findById(parkingTicketId);
        if (existingParkingTicket.isPresent()) {

            ParkingTicket existingData = existingParkingTicket.get();
            Instant exitTime=Instant.now();
            Instant entryTime=existingData.getEntryTime();

            Duration duration = Duration.between(entryTime, exitTime);
            long seconds = duration.getSeconds();
            double hours = seconds / 3600.0;
            BigDecimal fareAmount = existingData.getUnitPrice().multiply(BigDecimal.valueOf(hours));

            existingData.setDuration(hours);
            existingData.setExitTime(exitTime);
            existingData.setFareAmount(fareAmount);
            existingData.setParkingTicketStatusEnum(ParkingTicketStatusEnum.CLOSED);

            return ParkingTicketDto.fromEntity(
                    parkingTicketRepository.save(existingData)
            );

        }
        else {

            throw new EntityNotFoundException("Aucune ticket de parking avec l'ID = " +parkingTicketId+ " n' a ete trouve dans la BDD",
                    ErrorCodes.PARKINGTICKET_NOT_FOUND);
        }
    }

    @Override
    public ParkingTicketDto findById(Long id) {
        return null;
    }

    @Override
    public Page<ParkingTicketDto> findAllParkingTicket(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
