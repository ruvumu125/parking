package com.parking.dto;

import com.parking.model.Deposit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositDto {

    private Long id;
    private TransactionDto transaction;

    public static DepositDto fromEntity(Deposit deposit) {
        if(deposit == null) {
            return null;
        }

        return DepositDto.builder()
                .id(deposit.getId())
                .transaction(TransactionDto.fromEntity(deposit.getTransaction()))
                .build();
    }

    public static Deposit toEntity(DepositDto depositDto) {
        if(depositDto == null) {
            return null;
        }

        Deposit deposit = new Deposit();
        deposit.setId(depositDto.getId());
        deposit.setTransaction(TransactionDto.toEntity(depositDto.getTransaction()));

        return deposit;
    }
}
