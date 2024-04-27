package com.parking.dto;

import com.parking.model.VehiculeAccount;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class VehiculeAccountDto {

    private Long id;
    private String accountNumber;
    private VehicleDto vehicle;
    private byte[] qrCodeImage;
    private String qrCodeString;
    private Instant openDate;

    public static VehiculeAccountDto fromEntity(VehiculeAccount account) {
        if(account == null) {
            return null;
        }

        return VehiculeAccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .vehicle(VehicleDto.fromEntity(account.getVehicle()))
                .qrCodeImage(account.getQrCodeImage())
                .qrCodeString(account.getQrCodeString())
                .openDate(account.getOpenDate())
                .build();
    }

    public static VehiculeAccount toEntity(VehiculeAccountDto accountDto) {
        if(accountDto == null) {
            return null;
        }

        VehiculeAccount account = new VehiculeAccount();
        account.setId(accountDto.getId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setVehicle(VehicleDto.toEntity(accountDto.getVehicle()));
        account.setQrCodeImage(accountDto.getQrCodeImage());
        account.setQrCodeString(accountDto.getQrCodeString());
        account.setOpenDate(accountDto.getOpenDate());

        return account;
    }
}
