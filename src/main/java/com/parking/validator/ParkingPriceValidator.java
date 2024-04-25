package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.parking.dto.ParkingPriceDto;

public class ParkingPriceValidator {

    public static List<String> validate(ParkingPriceDto parkingPriceDto){
        List<String> errors = new ArrayList<>();

        if(parkingPriceDto == null) {
            errors.add("Veuiller selectionner un type de vehicule");
            errors.add("Veuiller renseigner le prix");
            return errors;
        }

        if(parkingPriceDto.getCompanyDto() == null || parkingPriceDto.getCompanyDto().getId() == null ||  parkingPriceDto.getCompanyDto().getId().compareTo(0L) == 0) {
            errors.add("Entreprise ne doit pas etre null");
        }

        if(parkingPriceDto.getVehicleTypeDto() == null || parkingPriceDto.getVehicleTypeDto().getId() == null ||  parkingPriceDto.getVehicleTypeDto().getId().compareTo(0L) == 0) {
            errors.add("Veuiller selectionner un type de vehicule");
        }

        if(parkingPriceDto.getPrice() == null) {
            errors.add("Veuiller renseigner un prix");
        }
        if(parkingPriceDto.getPrice() == null || parkingPriceDto.getPrice().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Le prix doit etere superieur a zero");
        }
        return errors;
    }
}
