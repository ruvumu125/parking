package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.dto.UserDto;
import com.parking.model.Superadmin;
import com.parking.model.SuperadminTypeEnum;
import com.parking.model.User;
import com.parking.model.UserRoleEnum;
import com.parking.services.MailSenderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.SuperadminDto;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.SuperadminRepository;
import com.parking.repository.UserRepository;
import com.parking.services.SuperadminService;
import com.parking.validator.SuperadminValidator;
import com.parking.exceptions.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuperadminServiceImpl implements SuperadminService {
	
	private final SuperadminRepository superadminRepository;
	private final UserRepository userRepository;
	private final MailSenderService mailService;

	@Autowired
	public SuperadminServiceImpl(SuperadminRepository superadminRepository, UserRepository userRepository, MailSenderService mailService) {
		this.superadminRepository = superadminRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}
	
	@Override
	public SuperadminDto save(SuperadminDto superadminDto) {
		List<String> errors = SuperadminValidator.validate(superadminDto);
		if(!errors.isEmpty()) {
			log.error("Superadmin is not valid {}", superadminDto);
			throw new InvalidEntityException("Le superadmin n'est pas valide", ErrorCodes.SUPERADMIN_NOT_VALID, errors);
		}

		if (superadminDto.getId() ==null || superadminDto.getId().compareTo(0L) == 0){

			if (userAlreadyExists(superadminDto.getUser().getUserEmail())){

				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
			if(phoneNumberAlreadyExists(superadminDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}

			//save section
			Optional<Superadmin> mainSuperAdmin = superadminRepository.findMainSuperAdmin();

			if (mainSuperAdmin.isPresent()){
				superadminDto.setSuperadminTypeEnum(SuperadminTypeEnum.SECONDARY_SUPERADMIN);
			}
			else{
				superadminDto.setSuperadminTypeEnum(SuperadminTypeEnum.MAIN_SUPERADMIN);
			}

			User user=new User();
			String noEncrypted_password=generateCommonLangPassword();
			user.setUserFullName(superadminDto.getUser().getUserFullName());
			user.setUserEmail(superadminDto.getUser().getUserEmail());
			user.setUserRoleEnum(UserRoleEnum.SUPERADMIN);
			user.setUserPhoneNumber(superadminDto.getUser().getUserPhoneNumber());
			user.setUserPassword(noEncrypted_password);
			user.setIsUserActive(true);
			User savedUser = userRepository.save(user);
			superadminDto.setUser(UserDto.fromEntity(savedUser));

			//send email
			mailService.sendNewMail(superadminDto.getUser().getUserEmail().trim(), "User password", "Dear user this is your password "+noEncrypted_password);

			return SuperadminDto.fromEntity(
					superadminRepository.save(SuperadminDto.toEntity(superadminDto))
			);

		}

		//update section
		if (!superadminDto.getUser().getUserEmail().equals(userEmail(superadminDto.getUser().getId())) && userAlreadyExists(superadminDto.getUser().getUserEmail())){

			throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
					Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDDp"));

		}
		if (!superadminDto.getUser().getUserPhoneNumber().equals(userPhoneNumber(superadminDto.getUser().getId())) && phoneNumberAlreadyExists(superadminDto.getUser().getUserPhoneNumber())){

			throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
					Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));

		}
		String pswd="";
		if (!superadminDto.getUser().getUserEmail().equals(userEmail(superadminDto.getUser().getId())) ){

			//change existing password and send new password to new user
			pswd=generateCommonLangPassword();
		}
		else{
			//get existing password
			pswd=userRepository.findUserByIdUser(superadminDto.getUser().getId()).getUserPassword();
		}


		Superadmin superadmin=superadminRepository.findSuperAdminById(superadminDto.getId());
		User user=superadmin.getUser();
		user.setUserFullName(superadminDto.getUser().getUserFullName());
		user.setUserEmail(superadminDto.getUser().getUserEmail());
		user.setUserPhoneNumber(superadminDto.getUser().getUserPhoneNumber());
		user.setUserPassword(pswd);

		if (!superadminDto.getUser().getUserEmail().equals(userEmail(superadminDto.getUser().getId())) ){

			//send email
			mailService.sendNewMail(superadminDto.getUser().getUserEmail().trim(), "Passord", "Dear user, this is your password "+pswd);
		}
		
		return SuperadminDto.fromEntity(
				superadminRepository.save(SuperadminDto.toEntity(superadminDto))
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
	public SuperadminDto findById(Long id) {
		if(id == null) {
			log.error("Superadmin is null");
		}
		return superadminRepository.findById(id)
				.map(SuperadminDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucun superadmin avec l'ID = " +id+ " n'a ete trouve dans la BDD",
						ErrorCodes.SUPERADMIN_NOT_FOUND));
	}

	@Override
	public List<SuperadminDto> findAll() {
		
		return superadminRepository.findAll().stream()
				.map(SuperadminDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Superadmin ID is null");
		}
		
		superadminRepository.deleteById(id);
		
	}

}
