package com.parking.services;

import java.util.List;

import com.parking.dto.ParkingSpaceDto;
import com.parking.dto.VehicleTypeDto;
import com.parking.model.ParkingSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParkingSpaceService {

    ParkingSpaceDto save(ParkingSpaceDto dto);

    ParkingSpaceDto findById(Long id);

    List<ParkingSpaceDto> findAll();

    Page<ParkingSpaceDto> findByNameLike(Long idCompany,String search, Pageable pageable);

    List<ParkingSpaceDto> findCompanyParkingSpaces(Long idCompany);

    void delete(Long id);
}
