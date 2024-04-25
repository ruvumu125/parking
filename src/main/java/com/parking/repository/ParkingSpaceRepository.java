package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.Company;
import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.ParkingSpace;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    @Query(value = "select p from ParkingSpace p where p.id = :id AND p.company.id =:company_id")
    ParkingSpace findParkingSpaceById(@Param("id") Long id,@Param("company_id") Long company_id);

    @Query(value = "select p from ParkingSpace p where p.name = :name AND p.company.id =:company_id")
    Optional<ParkingSpace> findParkingSpaceByName(@Param("name") String name,@Param("company_id") Long company_id);

    @Query(value = "select p from ParkingSpace p where p.company.id=?1 order by p.id desc ")
    Page<ParkingSpace> findAllParkingSpace(Long idCompany,Pageable pageable);

    @Query("select p from ParkingSpace p where p.company.id=?1 AND UPPER(p.name) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(p.location) like CONCAT('%',UPPER(?2),'%' ) order by p.id desc")
    Page<ParkingSpace> findByNameLike(Long idCompany,String search, Pageable pageable);

    @Query("select p from ParkingSpace p where p.company.id=?1 ")
    List<ParkingSpace> findCompanyParkingSpaces(Long idCompany);
}
