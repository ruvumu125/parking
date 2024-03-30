package com.parking.services;

import java.util.List;

import com.parking.dto.VehicleTypeDto;

public interface VehicleTypeService {
	
	VehicleTypeDto save(VehicleTypeDto dto);
	
	VehicleTypeDto findById(Long id);
	
	List<VehicleTypeDto> findAll();
	
	void delete(Long id);
}
