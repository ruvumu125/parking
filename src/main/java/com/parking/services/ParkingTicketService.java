package com.parking.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parking.dto.ParkingTicketDto;

public interface ParkingTicketService {

    ParkingTicketDto entryParking(ParkingTicketDto dto);

    ParkingTicketDto exitParking(Long parkingTicketId);

    ParkingTicketDto findById(Long id);

    Page<ParkingTicketDto> findAllParkingTicket(Pageable pageable);

    void delete (Long id);
}
