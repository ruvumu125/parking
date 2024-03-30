package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<CompanyDto> findAll() {
		
		return companyService.findAll();
	}

	@Override
	public void delete(Long id) {
		companyService.delete(id);
		
	}

}
