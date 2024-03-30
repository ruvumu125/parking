package com.parking.services;

import java.util.List;

import com.parking.dto.CompanyDto;

public interface CompanyService {
	
	CompanyDto save(CompanyDto dto);
	
	CompanyDto findById(Long id);
	
	List<CompanyDto> findAll();
	
	void delete(Long id);
}
