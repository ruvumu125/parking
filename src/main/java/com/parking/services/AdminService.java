package com.parking.services;

import java.util.List;

import com.parking.dto.AdminDto;

public interface AdminService {
	
	AdminDto save(AdminDto dto);
	
	AdminDto findById(Long id);
	
	List<AdminDto> findAll();
	
	void delete(Long id);
}
