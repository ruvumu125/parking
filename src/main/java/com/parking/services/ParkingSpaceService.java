package com.parking.services;

import java.util.List;

import com.parking.dto.ParkingSpaceDto;

public interface ParkingSpaceService {

    ParkingSpaceDto save(ParkingSpaceDto dto);

    ParkingSpaceDto findById(Long id);

    List<ParkingSpaceDto> findAll();

    List<ParkingSpaceDto> findCompanyParkingSpaces(Long idCompany);

    void delete(Long id);
}
