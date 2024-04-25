package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.dto.AdminDto;
import com.parking.dto.CompanyListDto;
import com.parking.dto.UserDto;
import com.parking.model.*;
import com.parking.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.dto.CompanyDto;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.AdminRepository;
import com.parking.repository.AgentRepository;
import com.parking.repository.CompanyRepository;
import com.parking.services.CompanyService;
import com.parking.validator.CompanyValidator;
import com.parking.exceptions.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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

		if ((dto.getId() ==null || dto.getId().compareTo(0L) == 0)){

			if(companyAlreadyExists(dto.getCompanyName())) {
				throw new InvalidEntityException("Une autre entreprise avec le meme nom existe deja", ErrorCodes.COMPANY_ALREADY_EXISTS,
						Collections.singletonList("Une autre entreprise avec le meme nom existe deja dans BDD"));
			}

			if(companyPhoneNumberAlreadyExists(dto.getCompanyPhoneNumber())) {
				throw new InvalidEntityException("Une autre entreprise avec le meme numero de telephone existe deja", ErrorCodes.COMPANY_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Une autre entreprise avec le meme numero de telephone existe deja dans la BDD"));
			}

			return CompanyDto.fromEntity(
					companyRepository.save(CompanyDto.toEntity(dto))
			);
		}

		Company existingCompany=companyRepository.findCompanyById(dto.getId());
		if (existingCompany !=null && !existingCompany.getCompanyName().equals(dto.getCompanyName())){

			if(companyAlreadyExists(dto.getCompanyName())) {
				throw new InvalidEntityException("Une autre entreprise avec le meme nom existe deja", ErrorCodes.COMPANY_ALREADY_EXISTS,
						Collections.singletonList("Une autre entreprise avec le meme nom existe deja dans BDD"));
			}
		}

		if (existingCompany !=null && !existingCompany.getCompanyPhoneNumber().equals(dto.getCompanyPhoneNumber())){

			if(companyPhoneNumberAlreadyExists(dto.getCompanyPhoneNumber())) {
				throw new InvalidEntityException("Une autre entreprise avec le meme numero de telephone existe deja", ErrorCodes.COMPANY_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Une autre entreprise avec le meme numero de telephone existe deja dans la BDD"));
			}
		}

		return CompanyDto.fromEntity(
				companyRepository.save(CompanyDto.toEntity(dto))
		);


	}

	private boolean companyPhoneNumberAlreadyExists(String phoneNumber) {
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
	public Page<CompanyListDto> findByNamePhoneAdressAdminLike(String search, Pageable pageable) {

		Page<Company> companies;
		if (search != null) {
			companies = companyRepository.findByNamePhoneAdressAdminLike(search, pageable);
		} else {
			// If no category is provided, fetch all products
			companies = companyRepository.findAllCompanies(pageable);
		}

		return companies.map(CompanyListDto::fromEntity);
	}


	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Company ID is null");
		}
		List<Admin> admin = adminRepository.findAllByCompanyId(id);
		if(!admin.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer cette entreprise qui est deja utilisé", 
					ErrorCodes.COMPANY_ALREADY_IN_USE);
		}
		companyRepository.deleteById(id);
		
	}

	@Override
	public List<CompanyDto> findCompaniesWithNoMainAdmin() {

		return companyRepository.findCompaniesWithNoMainAdmin().stream()
				.map(CompanyDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void enableCompany(Long companyId) {

		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (optionalCompany.isPresent()) {
			Company company = optionalCompany.get();
			company.setIsCompanyActive(true);
			companyRepository.save(company);
		} else {
			throw new EntityNotFoundException("Aucune entreprise avec l'ID = " +companyId+ " n' a ete trouve dans la BDD",ErrorCodes.COMPANY_NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public void desableCompany(Long companyId) {

		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (optionalCompany.isPresent()) {
			Company company = optionalCompany.get();
			company.setIsCompanyActive(false);
			companyRepository.save(company);
		} else {
			throw new EntityNotFoundException("Aucune entreprise avec l'ID = " +companyId+ " n' a ete trouve dans la BDD",ErrorCodes.COMPANY_NOT_FOUND);
		}
	}

}
