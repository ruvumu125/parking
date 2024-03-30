package com.parking.dto;

import com.parking.model.VehicleType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleTypeDto {
	
	private Long id;
	private String vehiculeTypeName;
	
	public static VehicleTypeDto fromEntity(VehicleType vehicleType) {
		if(vehicleType == null) {
			return null;
		}
		
		return VehicleTypeDto.builder()
				.id(vehicleType.getId())
				.vehiculeTypeName(vehicleType.getVehiculeTypeName())
				.build();
	}
	
	public static VehicleType toEntity(VehicleTypeDto vehicleTypeDto) {
		if(vehicleTypeDto == null) {
			return null;
		}
		
		VehicleType vehicleTypes = new VehicleType();
		vehicleTypes.setId(vehicleTypeDto.getId());
		vehicleTypes.setVehiculeTypeName(vehicleTypeDto.getVehiculeTypeName());
		
		return vehicleTypes;
	}
}
