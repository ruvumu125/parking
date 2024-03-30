package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.parking.dto.AdminDto;

public class AdminValidator {
	
	public static List<String> validate(AdminDto adminDto){
		List<String> errors = new ArrayList<>();
		
		if(adminDto == null) {
			errors.add("Veuillez renseigner le nom complet de l'utilisateur");
			errors.add("Veuillez renseigner l'email de l'utilisateur");
			errors.add("Veuillez renseigner le numero de telephone");
			errors.add("Veuillez renseigner une entreprise");
			return errors;
		}

		if (!StringUtils.hasLength(adminDto.getUser().getUserFullName())){
			errors.add("Veuillez renseigner le nom d'utilisateur");
		}

		if (!StringUtils.hasLength(adminDto.getUser().getUserEmail())){
			errors.add("Veuillez renseigner l'email de l'utilisateur");
		}
		if (!StringUtils.hasLength(adminDto.getUser().getUserPhoneNumber())){
			errors.add("Veuillez renseigner le numero de telephone");
		}
		
		if(adminDto.getCompany() == null || adminDto.getCompany().getId() == null || adminDto.getCompany().getId().compareTo(0L) == 0) {
			errors.add("Veuillez renseigner une entreprise");
		}
		
		return errors;
	}
}
