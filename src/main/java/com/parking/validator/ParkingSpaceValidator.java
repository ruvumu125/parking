package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.ParkingSpaceDto;

public class ParkingSpaceValidator {

    public static List<String> validate(ParkingSpaceDto parkingSpaceDto){
        List<String> errors = new ArrayList<>();

        if(parkingSpaceDto == null) {
            errors.add("Veuiller renseigner un nom");
            errors.add("Veuiller renseigner la localisation du parking");
            return errors;
        }

        if(!StringUtils.hasLength(parkingSpaceDto.getName())) {
            errors.add("Veuiller renseigner un nom d'un parking");
        }

        if(!StringUtils.hasLength(parkingSpaceDto.getLocation())) {
            errors.add("Veuiller renseigner la localisation du parking");
        }

        return errors;
    }
}
