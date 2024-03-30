package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.VehicleTypeDto;

public class VehicleTypeValidator {
	
	public static List<String> validate(VehicleTypeDto vehicleTypeDto){
		List<String> errors = new ArrayList<>();
		
		if(vehicleTypeDto == null || !StringUtils.hasLength(vehicleTypeDto.getVehiculeTypeName())) {
			errors.add("Veuillez renseigner un nom du type de vehicule");
		}
		return errors;
	}
}
