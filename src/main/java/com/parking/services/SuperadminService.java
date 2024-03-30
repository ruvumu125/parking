package com.parking.services;

import java.util.List;

import com.parking.dto.SuperadminDto;

public interface SuperadminService {
	
	SuperadminDto save(SuperadminDto dto);
	
	SuperadminDto findById(Long id);
	
	List<SuperadminDto> findAll();
	
	void delete(Long id);
}
