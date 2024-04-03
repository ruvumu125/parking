package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.ParkingSpaceApi;
import com.parking.dto.ParkingSpaceDto;
import com.parking.services.ParkingSpaceService;

@RestController
public class ParkingSpaceController implements ParkingSpaceApi {

    private final ParkingSpaceService parkingSpaceService;

    @Autowired
    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @Override
    public ParkingSpaceDto save(ParkingSpaceDto dto) {

        return parkingSpaceService.save(dto);
    }

    @Override
    public ParkingSpaceDto findById(Long id) {

        return parkingSpaceService.findById(id);
    }

    @Override
    public List<ParkingSpaceDto> findAll() {

        return parkingSpaceService.findAll();
    }

    @Override
    public List<ParkingSpaceDto> findCompanyParkingSpaces(Long idCompany) {
        return parkingSpaceService.findCompanyParkingSpaces(idCompany);
    }

    @Override
    public void delete(Long id) {
        parkingSpaceService.delete(id);

    }

}
