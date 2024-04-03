package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.dto.*;
import com.parking.model.*;
import com.parking.repository.UserRepository;
import com.parking.services.MailSenderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.AgentRepository;
import com.parking.services.AgentService;
import com.parking.validator.AgentValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {
	
	private final AgentRepository agentRepository;
	private final UserRepository userRepository;
	private final MailSenderService mailService;
	
	@Autowired
	public AgentServiceImpl(AgentRepository agentRepository, UserRepository userRepository, MailSenderService mailService) {
		this.agentRepository = agentRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	@Override
	public AgentDto save(AgentDto agentDto) {

		List<String> errors = AgentValidator.validate(agentDto);
		if(!errors.isEmpty()) {
			log.error("Agent is not valid {}", agentDto);
			throw new InvalidEntityException("L'agent n'est pas valide", ErrorCodes.AGENT_NOT_VALID, errors);
		}

		if (agentDto.getId() ==null || agentDto.getId().compareTo(0L) == 0){

			if (userAlreadyExists(agentDto.getUser().getUserEmail())){

				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
			if(phoneNumberAlreadyExists(agentDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}

			//save user
			User user=new User();
			String noEncrypted_password=generateCommonLangPassword();
			user.setUserFullName(agentDto.getUser().getUserFullName());
			user.setUserEmail(agentDto.getUser().getUserEmail());
			user.setUserRoleEnum(UserRoleEnum.AGENT);
			user.setUserPhoneNumber(agentDto.getUser().getUserPhoneNumber());
			user.setUserPassword(generateCommonLangPassword());
			user.setIsUserActive(true);
			User savedUser = userRepository.save(user);
			agentDto.setUser(UserDto.fromEntity(savedUser));

			//send email
//			mailService.sendNewMail(agentDto.getUser().getUserEmail().trim(), "User password", "Dear user this is your password "+noEncrypted_password);

			return AgentDto.fromEntity(
					agentRepository.save(AgentDto.toEntity(agentDto))
			);


		}

		//update section
		if (!agentDto.getUser().getUserEmail().equals(userEmail(agentDto.getUser().getId())) && userAlreadyExists(agentDto.getUser().getUserEmail())){

			throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
					Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

		}
		if (!agentDto.getUser().getUserPhoneNumber().equals(userPhoneNumber(agentDto.getUser().getId())) && phoneNumberAlreadyExists(agentDto.getUser().getUserPhoneNumber())){

			throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
					Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));

		}
		String pswd="";
		if (!agentDto.getUser().getUserEmail().equals(userEmail(agentDto.getUser().getId())) ){

			//change existing password and send new password to new user
			pswd=generateCommonLangPassword();
		}
		else{
			//get existing password
			pswd=userRepository.findUserByIdUser(agentDto.getUser().getId()).getUserPassword();
		}


		Agent agent=agentRepository.findAgentById(agentDto.getId());
		User user=agent.getUser();
		user.setUserFullName(agentDto.getUser().getUserFullName());
		user.setUserEmail(agentDto.getUser().getUserEmail());
		user.setUserPhoneNumber(agentDto.getUser().getUserPhoneNumber());
		user.setUserPassword(pswd);

//		if (!agentDto.getUser().getUserEmail().equals(userEmail(agentDto.getUser().getId())) ){
//
//			//send email
//			mailService.sendNewMail(agentDto.getUser().getUserEmail().trim(), "Passord", "Dear user, this is your password "+pswd);
//		}

		return AgentDto.fromEntity(
				agentRepository.save(AgentDto.toEntity(agentDto))
		);
	}

	private boolean phoneNumberAlreadyExists(String phoneNumber) {
		Optional<User> user = userRepository.findUserByPhoneNumber(phoneNumber);
		return user.isPresent();
	}

	private boolean userAlreadyExists(String email) {
		Optional<User> user = userRepository.findUserByEmail(email);
		return user.isPresent();
	}

	public String generateCommonLangPassword() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);
		String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
				.concat(numbers)
				.concat(specialChar)
				.concat(totalChars);
		List<Character> pwdChars = combinedChars.chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		Collections.shuffle(pwdChars);
		return pwdChars.stream()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}

	private String userEmail(Long id){
		Optional<User> utilisateur = userRepository.findById(id);

		String user_email="";
		if(utilisateur.isPresent()) {
			User existingUser = utilisateur.get();
			user_email = existingUser.getUserEmail();
			//operate on existingCustomer
		}

		return user_email;

	}

	private String userPhoneNumber(Long id){
		Optional<User> utilisateur = userRepository.findById(id);

		String user_phone_number="";
		if(utilisateur.isPresent()) {
			User existingUser = utilisateur.get();
			user_phone_number = existingUser.getUserPhoneNumber();
			//operate on existingCustomer
		}

		return user_phone_number;
	}

	@Override
	public AgentDto findById(Long id) {
		if(id == null) {
			log.error("Agent ID is null");
		}
		return agentRepository.findById(id)
				.map(AgentDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun agent avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.AGENT_NOT_FOUND));
	}

	@Override
	public List<AgentDto> findAll() {
		
		return agentRepository.findAll().stream()
				.map(AgentDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgentDto> findCompanyAgents(Long idCompany) {

		if(idCompany == null) {
			log.error("Company ID is null");
		}
		return agentRepository.findCompanyAgents(idCompany).stream()
				.map(AgentDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Agent ID is null");
		}

		agentRepository.deleteById(id);
		
	}
}
