package com.parking.repository;

import java.util.Optional;

import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "select v from Vehicle v where v.id = :id")
    Vehicle findVehicleById(@Param("id") Long id);

    @Query(value = "select ve from Vehicle ve order by ve.id desc")
    Page<Vehicle> findAllVehicle(Pageable pageable);

    @Query(value = "select ve from Vehicle ve where ve.registrationNumber = :number")
    Optional<Vehicle> findVehicleByRegistrationNumber(@Param("number") String number);

    @Query(value = "select ve from Vehicle ve join VehicleType vt on ve.vehicleType.id=vt.id where UPPER(ve.registrationNumber) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(vt.vehiculeTypeName) like CONCAT('%',UPPER(?1),'%' ) order by ve.id desc ")
    Page<Vehicle> findByVehiculeRegistrationNumberLike(String search, Pageable pageable);
}
