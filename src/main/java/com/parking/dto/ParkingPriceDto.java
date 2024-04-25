package com.parking.dto;

import com.parking.model.ParkingPrice;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ParkingPriceDto {

    private Long id;
    private CompanyDto companyDto;
    private VehicleTypeDto vehicleTypeDto;
    private BigDecimal price;

    public static ParkingPriceDto fromEntity(ParkingPrice parkingPrice) {
        if(parkingPrice == null) {
            return null;
        }

        return ParkingPriceDto.builder()
                .id(parkingPrice.getId())
                .companyDto(CompanyDto.fromEntity(parkingPrice.getCompany()))
                .vehicleTypeDto(VehicleTypeDto.fromEntity(parkingPrice.getVehicleType()))
                .price(parkingPrice.getPrice())
                .build();
    }

    public static ParkingPrice toEntity(ParkingPriceDto parkingPriceDto) {
        if(parkingPriceDto == null) {
            return null;
        }

        ParkingPrice parkingPrice = new ParkingPrice();
        parkingPrice.setId(parkingPriceDto.getId());
        parkingPrice.setCompany(CompanyDto.toEntity(parkingPriceDto.getCompanyDto()));
        parkingPrice.setVehicleType(VehicleTypeDto.toEntity(parkingPriceDto.getVehicleTypeDto()));
        parkingPrice.setPrice(parkingPriceDto.getPrice());

        return parkingPrice;
    }
}
