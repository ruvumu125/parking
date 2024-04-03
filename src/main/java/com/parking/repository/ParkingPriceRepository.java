package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.ParkingPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {
    List<ParkingPrice> findAllById(Long id);

    @Query("select p from  ParkingPrice p where p.company.id= :idCompany")
    List<ParkingPrice> findCompanyParkingPrices(@Param("idCompany") Long idCompany);

    @Query(value = "select p from ParkingPrice p where p.vehicleType.id =:vehicule_type_id AND p.company.id = :company_id ")
    Optional<ParkingPrice> findParkingPriceByCompanyAndVehiculeType(@Param("company_id") Long company_id,@Param("vehicule_type_id") Long vehicule_type_id);
}
