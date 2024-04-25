package com.parking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.model.Account;

import com.parking.model.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AccountDto {

    private Long id;
    private String accountNumber;
    private VehicleDto vehicle;
    private byte[] qrCodeImage;
    private String qrCodeString;
    private Instant openDate;

    public static AccountDto fromEntity(Account account) {
        if(account == null) {
            return null;
        }

        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .vehicle(VehicleDto.fromEntity(account.getVehicle()))
                .qrCodeImage(account.getQrCodeImage())
                .qrCodeString(account.getQrCodeString())
                .openDate(account.getOpenDate())
                .build();
    }

    public static Account toEntity(AccountDto accountDto) {
        if(accountDto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setVehicle(VehicleDto.toEntity(accountDto.getVehicle()));
        account.setQrCodeImage(accountDto.getQrCodeImage());
        account.setQrCodeString(accountDto.getQrCodeString());
        account.setOpenDate(accountDto.getOpenDate());

        return account;
    }
}
