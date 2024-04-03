package com.parking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.ParkingPriceApi;
import com.parking.dto.ParkingPriceDto;
import com.parking.services.ParkingPriceService;

@RestController
public class ParkingPriceController implements ParkingPriceApi {

    private final ParkingPriceService parkingPriceService;

    public ParkingPriceController(ParkingPriceService parkingPriceService) {
        this.parkingPriceService = parkingPriceService;
    }

    @Override
    public ParkingPriceDto save(ParkingPriceDto dto) {

        return parkingPriceService.save(dto);
    }

    @Override
    public ParkingPriceDto findById(Long id) {

        return parkingPriceService.findById(id);
    }

    @Override
    public List<ParkingPriceDto> findAll() {

        return parkingPriceService.findAll();
    }

    @Override
    public List<ParkingPriceDto> findCompanyParkingPrices(Long idCompany) {
        return parkingPriceService.findCompanyParkingPrices(idCompany);
    }

    @Override
    public void delete(Long id) {
        parkingPriceService.delete(id);

    }

}
