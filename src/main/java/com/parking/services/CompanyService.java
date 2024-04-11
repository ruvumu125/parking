package com.parking.services;

import java.util.List;

import com.parking.dto.CompanyDto;
import com.parking.dto.CompanyListDto;
import com.parking.dto.VehicleTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
	
	CompanyDto save(CompanyDto dto);
	
	CompanyDto findById(Long id);

	Page<CompanyListDto> findByNamePhoneAdressAdminLike(String search, Pageable pageable);
	
	void delete(Long id);

	List<CompanyDto> findCompaniesWithNoMainAdmin();

	void enableCompany(Long companyId);

	void desableCompany(Long companyId);
}
