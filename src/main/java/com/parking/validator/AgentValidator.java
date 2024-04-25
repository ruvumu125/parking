package com.parking.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parking.dto.AgentDto;
import org.springframework.util.StringUtils;

public class AgentValidator {
	
	public static List<String> validate(AgentDto agentDto){
		List<String> errors = new ArrayList<>();

		if(agentDto == null) {
			errors.add("Veuillez renseigner le nom complet de l'utilisateur");
			errors.add("Veuillez renseigner l'email de l'utilisateur");
			errors.add("Veuillez renseigner le numero de telephone");
			errors.add("Veuillez renseigner une entreprise");
			errors.add("Veuillez renseigner une place de parking");
			return errors;
		}

		if (!StringUtils.hasLength(agentDto.getUser().getUserFullName())){
			errors.add("Veuillez renseigner le nom d'utilisateur");
		}

		if (!StringUtils.hasLength(agentDto.getUser().getUserEmail())){
			errors.add("Veuillez renseigner l'email de l'utilisateur");
		}
		if (!isValidEmail(agentDto.getUser().getUserEmail())){
			errors.add("Email de l'utilisateur invalide");
		}
		if (!StringUtils.hasLength(agentDto.getUser().getUserPhoneNumber())){
			errors.add("Veuillez renseigner le numero de telephone");
		}

		if(agentDto.getCompany() == null || agentDto.getCompany().getId() == null || agentDto.getCompany().getId().compareTo(0L) == 0) {
			errors.add("Entreprise ne peut pas etre nul");
		}
		if(agentDto.getParkingSpace() == null || agentDto.getParkingSpace().getId() == null || agentDto.getParkingSpace().getId().compareTo(0L) == 0) {
			errors.add("Place de parking ne peut pas etre nul");
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
