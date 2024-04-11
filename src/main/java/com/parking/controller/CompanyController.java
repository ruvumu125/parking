package com.parking.controller;

import java.util.List;

import com.parking.dto.CompanyListDto;
import com.parking.dto.VehicleTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.CompanyApi;
import com.parking.dto.CompanyDto;
import com.parking.services.CompanyService;

@RestController
public class CompanyController implements CompanyApi {
	
	private final CompanyService companyService;
	
	@Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Override
	public CompanyDto save(CompanyDto dto) {
		
		return companyService.save(dto);
	}

	@Override
	public CompanyDto findById(Long id) {
		
		return companyService.findById(id);
	}

	@Override
	public Page<CompanyListDto> findAllCompanies(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return companyService.findByNamePhoneAdressAdminLike(search,pageable);
	}

	@Override
	public List<CompanyDto> findCompaniesWithNoMainAdmin() {
		return companyService.findCompaniesWithNoMainAdmin();
	}

	@Override
	public void enableCompany(Long id) {
		companyService.enableCompany(id);
	}

	@Override
	public void desableCompany(Long id) {
		companyService.desableCompany(id);
	}

	@Override
	public void delete(Long id) {
		companyService.delete(id);
		
	}

}
