package com.parking.dto;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDto {

    private Long id;
    private String registrationNumber;
    private VehicleTypeDto vehicleType;

    public static VehicleDto fromEntity(Vehicle vehicle) {
        if(vehicle == null) {
            return null;
        }

        return VehicleDto.builder()
                .id(vehicle.getId())
                .registrationNumber(vehicle.getRegistrationNumber())
                .vehicleType(VehicleTypeDto.fromEntity(vehicle.getVehicleType()))
                .build();
    }

    public static Vehicle toEntity(VehicleDto vehicleDto) {
        if(vehicleDto == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setRegistrationNumber(vehicleDto.getRegistrationNumber());
        vehicle.setVehicleType(VehicleTypeDto.toEntity(vehicleDto.getVehicleType()));
        return vehicle;
    }
}
