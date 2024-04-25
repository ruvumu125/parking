package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.ParkingTicketDto;

public class ParkingTicketValidator {

    public static List<String> validate(ParkingTicketDto parkingTicketDto){
        List<String> errors = new ArrayList<>();

        if(parkingTicketDto == null) {
            errors.add("Veuillez selectionner un vehicule");
            errors.add("Veuillez renseigner le prix unitaire");

            return errors;
        }

        if(parkingTicketDto.getVehicle() == null || parkingTicketDto.getVehicle().getId() == null ||  parkingTicketDto.getVehicle().getId().compareTo(0L) == 0) {
            errors.add("Veuillez selectionner un vehicule");
        }

        if(parkingTicketDto.getUnitPrice() == null ||  parkingTicketDto.getUnitPrice().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Veuillez renseigner le prix unitaire");
        }

        return errors;
    }
}
