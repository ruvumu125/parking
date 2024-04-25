package com.parking.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.parking.dto.SuperadminDto;

public class SuperadminValidator {
	
	public static List<String> validate(SuperadminDto superadminDto){
		List<String> errors = new ArrayList<>();
		
		if(superadminDto == null) {
			errors.add("Veuillez renseigner le nom complet de l'utilisateur");
			errors.add("Veuillez renseigner l'email de l'utilisateur");
			errors.add("Veuillez renseigner le numero de telephone");
			return errors;
		}

		if (!StringUtils.hasLength(superadminDto.getUser().getUserFullName())){
			errors.add("Veuillez renseigner le nom d'utilisateur");
		}

		if (!StringUtils.hasLength(superadminDto.getUser().getUserEmail())){
			errors.add("Veuillez renseigner l'email de l'utilisateur");
		}
		if (!isValidEmail(superadminDto.getUser().getUserEmail())){
			errors.add("Email de l'utilisateur invalide");
		}
		if (!StringUtils.hasLength(superadminDto.getUser().getUserPhoneNumber())){
			errors.add("Veuillez renseigner le numero de telephone");
		}
		
		return errors;
	}

	private static boolean isValidEmail(String email) {

		String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
