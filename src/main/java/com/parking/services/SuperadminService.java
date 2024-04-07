package com.parking.services;

import java.util.List;

import com.parking.dto.SuperadminDto;
import com.parking.dto.VehicleTypeDto;
import com.parking.model.Superadmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuperadminService {
	
	SuperadminDto save(SuperadminDto dto);
	
	SuperadminDto findById(Long id);

	Page<SuperadminDto> findByNameEmailPhoneLike(String search, Pageable pageable);
	
	void delete(Long id);
}
