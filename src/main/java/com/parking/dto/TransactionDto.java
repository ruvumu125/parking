package com.parking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.model.Deposit;
import com.parking.model.Payment;
import com.parking.model.Transaction;
import com.parking.model.TransactionTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class TransactionDto {

    private Long id;
    private String transactionCode;
    private VehiculeAccountDto account;
    private TransactionTypeEnum transactionType;
    private BigDecimal transactionAmount;
    private Instant transactionDate;

    @JsonIgnore
    private List<Payment> payments;
    @JsonIgnore
    private List<Deposit> deposits;

    public static TransactionDto fromEntity(Transaction transaction) {
        if(transaction == null) {
            return null;
        }

        return TransactionDto.builder()
                .id(transaction.getId())
                .transactionCode(transaction.getTransactionCode())
                .account(VehiculeAccountDto.fromEntity(transaction.getAccount()))
                .transactionType(transaction.getTransactionType())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        if(transactionDto == null) {
            return null;
        }

        Transaction transaction=new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setTransactionCode(transactionDto.getTransactionCode());
        transaction.setAccount(VehiculeAccountDto.toEntity(transactionDto.getAccount()));
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionAmount(transactionDto.getTransactionAmount());
        transaction.setTransactionDate(transactionDto.getTransactionDate());

        return transaction;
    }
}
