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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.AdminRepository;
import com.parking.services.AdminService;
import com.parking.validator.AdminValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
	
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final MailSenderService mailService;
	
	@Autowired
	public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, MailSenderService mailService) {
		this.adminRepository = adminRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}
	
	@Override
	public AdminDto save(AdminDto adminDto) {

		List<String> errors = AdminValidator.validate(adminDto);
		if(!errors.isEmpty()) {
			log.error("Admin is not valid {}", adminDto);
			throw new InvalidEntityException("L'admin n'est pas valide", ErrorCodes.ADMIN_NOT_VALID, errors);
		}

		if (adminDto.getId() ==null || adminDto.getId().compareTo(0L) == 0){

			if (userAlreadyExists(adminDto.getUser().getUserEmail())){

				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
			if(phoneNumberAlreadyExists(adminDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}
			//save
			Optional<Admin> mainAdmin = adminRepository.findMainAdminOfGivenCompany(adminDto.getCompany().getId());

			if (mainAdmin.isPresent()){
				adminDto.setAdminTypeEnum(AdminTypeEnum.SECONDARY_ADMIN);
			}
			else{
				adminDto.setAdminTypeEnum(AdminTypeEnum.MAIN_ADMIN);
			}

			String noEncrypted_password=generateCommonLangPassword();
			adminDto.getUser().setUserRoleEnum(UserRoleEnum.ADMIN);
			adminDto.getUser().setIsUserActive(true);
			adminDto.getUser().setUserPassword(generateCommonLangPassword());

			UserDto savedUser = UserDto.fromEntity(
					userRepository.save(UserDto.toEntity(adminDto.getUser()))
			);

			AdminDto adminDto1 = fromUser(savedUser,adminDto.getAdminTypeEnum(),adminDto.getCompany());
			AdminDto savedAdmin  = AdminDto.fromEntity(
					adminRepository.save(AdminDto.toEntity(adminDto1))
			);

			//send email
			mailService.sendNewMail(adminDto.getUser().getUserEmail().trim(), "User password", "Dear user this is your password "+noEncrypted_password);

			return savedAdmin;
		}

		//update section
		User existingUser=userRepository.findUserByIdUser(adminDto.getUser().getId());
		if (existingUser !=null && !existingUser.getUserEmail().equals(adminDto.getUser().getUserEmail())){

			if (userAlreadyExists(adminDto.getUser().getUserEmail())){

				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
		}
		if (existingUser !=null && !existingUser.getUserPhoneNumber().equals(adminDto.getUser().getUserPhoneNumber())){

			if(phoneNumberAlreadyExists(adminDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}
		}

		String pswd="";
		if (!adminDto.getUser().getUserEmail().equals(userEmail(adminDto.getUser().getId())) ){

			//change existing password and send new password to new user
			pswd=generateCommonLangPassword();
		}
		else{
			//get existing password
			pswd=userRepository.findUserByIdUser(adminDto.getUser().getId()).getUserPassword();
		}


		Admin admin=adminRepository.findAdminById(adminDto.getId());
		User user=admin.getUser();
		user.setUserFullName(adminDto.getUser().getUserFullName());
		user.setUserEmail(adminDto.getUser().getUserEmail());
		user.setUserPhoneNumber(adminDto.getUser().getUserPhoneNumber());
		user.setUserPassword(pswd);
		userRepository.save(user);

		AdminDto savedAdmin=AdminDto.fromEntity(adminRepository.save(AdminDto.toEntity(adminDto)));

		if (!adminDto.getUser().getUserEmail().equals(userEmail(adminDto.getUser().getId())) ){

			//send email
			mailService.sendNewMail(adminDto.getUser().getUserEmail().trim(), "Passord", "Dear user, this is your password "+pswd);
		}

		return savedAdmin;

	}

	private AdminDto fromUser(UserDto dto, AdminTypeEnum adminTypeEnum, CompanyDto companyDto) {

		return AdminDto.builder()
				.user(dto)
				.adminTypeEnum(adminTypeEnum)
				.company(companyDto)
				.build();
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
	public AdminDto findById(Long id) {
		if(id == null) {
			log.error("Admin ID is null");
		}
		return adminRepository.findById(id)
				.map(AdminDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun admin avec l'ID = " +id+ " n'a ete trouve dans la BDD",
						ErrorCodes.ADMIN_NOT_FOUND));
	}

	@Override
	public List<AdminDto> findAll() {
		
		return adminRepository.findAll().stream()
				.map(AdminDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<AdminDto> findAllMainAdmins(String search, Pageable pageable) {

		Page<Admin> admins;
		if (search != null) {
			admins = adminRepository.findMainAdminsByNameEmailPhoneLike(search, pageable);
		} else {
			admins = adminRepository.findAllMainAdmins(pageable);
		}

		return admins.map(AdminDto::fromEntity);
	}

	@Override
	public Page<AdminDto> findCompanyAdmins(Long idCompany, String search, Pageable pageable) {

		Page<Admin> admins;
		if (search != null) {
			admins = adminRepository.findCompanyAdminsByNameEmailPhoneLike(idCompany,search, pageable);
		} else {
			admins = adminRepository.findCompanyAdmins(idCompany,pageable);
		}

		return admins.map(AdminDto::fromEntity);
	}

	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Admin ID is null");
		}
		
		adminRepository.deleteById(id);
		
	}

}
