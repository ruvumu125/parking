package com.parking.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<ParkingPriceDto> findCompanyParkingPrices(Long idCompany, String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return parkingPriceService.findByNameLike(idCompany,search,pageable);
    }

    @Override
    public void delete(Long id) {
        parkingPriceService.delete(id);

    }

}
