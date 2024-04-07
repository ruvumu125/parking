package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.CompanyDto;

public class CompanyValidator {
	
	public static List<String> validate(CompanyDto companyDto){
		List<String> errors = new ArrayList<>();
		
		if(companyDto == null) {
			errors.add("Veuillez renseigner un nom");
			errors.add("Veuillez renseigner un numero de telephone");
			errors.add("Veuillez renseigner une adresse");

			return errors;
		}
		
		if(!StringUtils.hasLength(companyDto.getCompanyName())) {
			errors.add("Veuillez renseigner un nom");
		}
		
		if(!StringUtils.hasLength(companyDto.getCompanyPhoneNumber())) {
			errors.add("Veuillez renseigner un numero de telephone");
		}
		
		if(!StringUtils.hasLength(companyDto.getCompanyAddress())) {
			errors.add("Veuillez renseigenr une adresse");
		}


		
		return errors;
	}
}
