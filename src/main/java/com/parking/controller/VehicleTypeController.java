package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<VehicleTypeDto> findAll() {
		
		return vehicleTypeService.findAll();
	}

	@Override
	public void delete(Long id) {
		vehicleTypeService.delete(id);
		
	}
	
}
