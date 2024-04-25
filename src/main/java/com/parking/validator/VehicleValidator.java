package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.VehicleDto;

public class VehicleValidator {

    public static List<String> validate(VehicleDto vehicleDto){
        List<String> errors = new ArrayList<>();

        if(vehicleDto == null) {
            errors.add("Veuillez selectionner le type de vehicule");
            errors.add("Veuillez renseigner le numero de plaque");
            return errors;
        }

        if(!StringUtils.hasLength(vehicleDto.getRegistrationNumber())) {
            errors.add("Veuillez renseigner le numero de plaque");
        }

        if(vehicleDto.getVehicleType() == null || vehicleDto.getVehicleType().getId() == null) {
            errors.add("Veuillez selectionner le type de vehicule");
        }

        return errors;
    }
}
