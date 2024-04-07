package com.parking.services;

import java.util.List;

import com.parking.dto.ParkingPriceDto;
import com.parking.dto.ParkingSpaceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParkingPriceService {

    ParkingPriceDto save(ParkingPriceDto dto);

    ParkingPriceDto findById(Long id);

    List<ParkingPriceDto> findAll();

    Page<ParkingPriceDto> findByNameLike(Long idCompany, String search, Pageable pageable);

    void delete(Long id);
}
