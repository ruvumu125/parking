package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.VehicleApi;
import com.parking.dto.VehicleDto;
import com.parking.services.VehicleService;

@RestController
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public VehicleDto save(VehicleDto dto) {

        return vehicleService.save(dto);
    }

    @Override
    public VehicleDto findById(Long id) {

        return vehicleService.findById(id);
    }

    @Override
    public Page<VehicleDto> findByVehiculeRegistrationNumber(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vehicleService.findByVehicleRegistrationNumber(search, pageable);
    }

    @Override
    public void delete(Long id) {
        vehicleService.delete(id);

    }

}
