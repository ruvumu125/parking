package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.ParkingSpace;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findAllById(Long id);

    @Query(value = "select p from ParkingSpace p where p.name = :name AND p.company.id =:company_id")
    Optional<ParkingSpace> findParkingSpaceByName(@Param("name") String name,@Param("company_id") Long company_id);

    @Query("select p from  ParkingSpace p where p.company.id= :idCompany")
    List<ParkingSpace> findCompanyParkingSpaces(@Param("idCompany") Long idCompany);
}
