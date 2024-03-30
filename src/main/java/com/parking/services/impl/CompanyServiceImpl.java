package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.CompanyDto;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.model.Admin;
import com.parking.model.Agent;
import com.parking.model.Company;
import com.parking.repository.AdminRepository;
import com.parking.repository.AgentRepository;
import com.parking.repository.CompanyRepository;
import com.parking.services.CompanyService;
import com.parking.validator.CompanyValidator;
import com.parking.exceptions.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyRepository companyRepository;
	private final AdminRepository adminRepository;
	private final AgentRepository agentRepository;
	
	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository, AdminRepository adminRepository, AgentRepository agentRepository) {
		this.companyRepository = companyRepository;
		this.adminRepository = adminRepository;
		this.agentRepository = agentRepository;
	}
	
	@Override
	public CompanyDto save(CompanyDto dto) {
		// TODO Auto-generated method stub
		List<String> errors = CompanyValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Company is not valid {}", dto);
			throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.COMPANY_NOT_VALID, errors);
		}

		if(companyAlreadyExists(dto.getCompanyName())) {
			throw new InvalidEntityException("Une autre entreprise avec le meme nom existe deja", ErrorCodes.COMPANY_ALREADY_EXISTS,
					Collections.singletonList("Une autre entreprise avec le meme nom existe deja dans BDD"));
		}

		if(phoneNumberAlreadyExists(dto.getCompanyPhoneNumber())) {
			throw new InvalidEntityException("Une autre entreprise avec le meme numero de telephone existe deja", ErrorCodes.COMPANY_PHONE_NUMBER_ALREADY_EXISTS,
					Collections.singletonList("Une autre entreprise avec le meme numero de telephone existe deja dans la BDD"));
		}
		
		return CompanyDto.fromEntity(
				companyRepository.save(CompanyDto.toEntity(dto))
		);
	}

	private boolean phoneNumberAlreadyExists(String phoneNumber) {
		Optional<Company> company = companyRepository.findCompanyByPhoneNumber(phoneNumber);
		return company.isPresent();
	}

	private boolean companyAlreadyExists(String name) {
		Optional<Company> company = companyRepository.findCompanyByName(name);
		return company.isPresent();
	}

	@Override
	public CompanyDto findById(Long id) {
		
		if(id == null) {
			log.error("Company ID is null");
			return null;
		}
		return companyRepository.findById(id)
				.map(CompanyDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucune entreprise avec l'ID = " +id+ " n' a ete trouve dans la BDD",
						ErrorCodes.COMPANY_NOT_FOUND)
						);
	}

	@Override
	public List<CompanyDto> findAll() {
		// TODO Auto-generated method stub
		return companyRepository.findAll().stream()
				.map(CompanyDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Company ID is null");
		}
		List<Admin> admin = adminRepository.findAllById(id);
		List<Agent> agent = agentRepository.findAllById(id);
		if(!admin.isEmpty() || !agent.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer cette entreprise qui est deja utilisé", 
					ErrorCodes.COMPANY_ALREADY_IN_USE);
		}
		companyRepository.deleteById(id);
		
	}

}
