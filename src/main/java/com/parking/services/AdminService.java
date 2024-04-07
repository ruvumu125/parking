package com.parking.services;

import java.util.List;

import com.parking.dto.AdminDto;
import com.parking.dto.VehicleTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
	
	AdminDto save(AdminDto dto);
	
	AdminDto findById(Long id);

	List<AdminDto> findAll();

	Page<AdminDto> findAllMainAdmins(String search, Pageable pageable);

	Page<AdminDto> findCompanyAdmins(Long idCompany,String search, Pageable pageable);

	
	void delete(Long id);
}
