package com.parking.validator;

import java.util.ArrayList;
import java.util.List;

import com.parking.dto.AgentDto;

public class AgentValidator {
	
	public static List<String> validate(AgentDto agentDto){
		List<String> errors = new ArrayList<>();
		
		if(agentDto == null) {
			errors.add("Veuillez renseigner un utilisateur");
			errors.add("Veuillez renseigner une entreprise");
		}
		
		if(agentDto.getUser() == null || agentDto.getUser().getId() == null) {
			errors.add("Veuillez renseigner un utilisateur");
		}
		
		if(agentDto.getCompany() == null || agentDto.getCompany().getId() == null) {
			errors.add("Veuillez renseigner une entreprise");
		}
		
		return errors;
	}
}
