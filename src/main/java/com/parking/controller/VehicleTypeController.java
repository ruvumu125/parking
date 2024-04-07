package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.VehicleTypeApi;
import com.parking.dto.VehicleTypeDto;
import com.parking.services.VehicleTypeService;

@RestController
public class VehicleTypeController implements VehicleTypeApi {
	
	private final VehicleTypeService vehicleTypeService;
	
	@Autowired
	public VehicleTypeController(VehicleTypeService vehicleTypeService) {
		this.vehicleTypeService = vehicleTypeService;
	}

	@Override
	public VehicleTypeDto save(VehicleTypeDto dto) {

		return vehicleTypeService.save(dto);
	}

	@Override
	public VehicleTypeDto findById(Long id) {
		
		return vehicleTypeService.findById(id);
	}

	@Override
	public Page<VehicleTypeDto> findByVehiculeTypeName(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return vehicleTypeService.findByVehiculeTypeName(search,pageable);
	}

	@Override
	public List<VehicleTypeDto> findAvailableVehiculeTypeGivenCompany(Long idCompany) {
		return vehicleTypeService.findAvailableVehiculeTypeGivenCompany(idCompany);
	}


	@Override
	public void delete(Long id) {
		vehicleTypeService.delete(id);
		
	}
	
}
