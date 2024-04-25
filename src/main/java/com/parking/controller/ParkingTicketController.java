package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.ParkingTicketApi;
import com.parking.dto.ParkingTicketDto;
import com.parking.services.ParkingTicketService;

@RestController
public class ParkingTicketController implements ParkingTicketApi {

    private final ParkingTicketService parkingTicketService;

    @Autowired
    public ParkingTicketController(ParkingTicketService parkingTicketService) {
        this.parkingTicketService = parkingTicketService;
    }

    @Override
    public ParkingTicketDto entryParking(ParkingTicketDto dto) {
        return parkingTicketService.entryParking(dto);
    }

    @Override
    public ParkingTicketDto exitParking(Long parkingTicketId) {
        return parkingTicketService.exitParking(parkingTicketId);
    }

    @Override
    public ParkingTicketDto findById(Long id) {
        return null;
    }

    @Override
    public Page<ParkingTicketDto> findAllParkingTicket(int page, int size) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
