package com.parking.dto;

import com.parking.model.ParkingSpace;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingSpaceDto {

    private Long id;
    private String name;
    private String location;
    private CompanyDto company;

    public static ParkingSpaceDto fromEntity(ParkingSpace parkingSpace) {
        if(parkingSpace == null) {
            return null;
        }

        return ParkingSpaceDto.builder()
                .id(parkingSpace.getId())
                .name(parkingSpace.getName())
                .location(parkingSpace.getLocation())
                .company(CompanyDto.fromEntity(parkingSpace.getCompany()))
                .build();
    }

    public static ParkingSpace toEntity(ParkingSpaceDto parkingSpaceDto) {
        if(parkingSpaceDto == null) {
            return null;
        }

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(parkingSpaceDto.getId());
        parkingSpace.setName(parkingSpaceDto.getName());
        parkingSpace.setLocation(parkingSpaceDto.getLocation());
        parkingSpace.setCompany(CompanyDto.toEntity(parkingSpaceDto.getCompany()));

        return parkingSpace;
    }


}
