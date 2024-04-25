package com.parking.repository;
import com.parking.model.Company;
import com.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.model.ParkingTicket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    @Query(value = "select pt from ParkingTicket pt where pt.vehicle.registrationNumber = :number AND pt.parkingTicketPaymentStatusEnum = 'UNPAID' ")
    Optional<ParkingTicket> findVehicleUnpaidTicketByRegistrationNumber(@Param("number") String number);

    @Query(value = "select pt from ParkingTicket pt where pt.id = :id AND pt.parkingTicketPaymentStatusEnum = 'PAID'")
    Optional<ParkingTicket> findVehiclePaidTicketById(@Param("id") Long id);

    @Query(value = "select pt from ParkingTicket pt where pt.id = :id AND pt.parkingTicketStatusEnum = 'CLOSED'")
    Optional<ParkingTicket> findVehicleCloseTicketById(@Param("id") Long id);

}
