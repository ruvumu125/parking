package com.parking.services;

import java.util.List;

import com.parking.dto.ParkingPriceDto;
import com.parking.dto.ParkingSpaceDto;

public interface ParkingPriceService {

    ParkingPriceDto save(ParkingPriceDto dto);

    ParkingPriceDto findById(Long id);

    List<ParkingPriceDto> findAll();

    List<ParkingPriceDto> findCompanyParkingPrices(Long idCompany);

    void delete(Long id);
}
