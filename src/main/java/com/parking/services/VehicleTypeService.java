package com.parking.services;

import java.util.List;

import com.parking.dto.VehicleTypeDto;
import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleTypeService {
	
	VehicleTypeDto save(VehicleTypeDto dto);
	
	VehicleTypeDto findById(Long id);

	Page<VehicleTypeDto> findByVehiculeTypeName(String search, Pageable pageable);

	List<VehicleTypeDto> findAvailableVehiculeTypeGivenCompany(Long idCompany);
	
	void delete(Long id);
}
