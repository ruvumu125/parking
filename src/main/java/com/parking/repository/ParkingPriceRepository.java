package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.ParkingSpace;
import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.ParkingPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {

    @Query(value = "select p from ParkingPrice p where p.id = :id AND p.company.id =:company_id")
    ParkingPrice findParkingPriceById(@Param("id") Long id,@Param("company_id") Long company_id);


    @Query(value = "select p from ParkingPrice p where p.vehicleType.id =:vehicule_type_id AND p.company.id = :company_id ")
    Optional<ParkingPrice> findParkingPriceByCompanyAndVehiculeType(@Param("company_id") Long company_id,@Param("vehicule_type_id") Long vehicule_type_id);

    @Query(value = "select p from ParkingPrice p where p.company.id=?1 order by p.id desc ")
    Page<ParkingPrice> findCompanyParkingPrices(Long idCompany, Pageable pageable);

    @Query("select p from ParkingPrice p where p.company.id=?1 AND UPPER(p.vehicleType.vehiculeTypeName) like CONCAT('%',UPPER(?2),'%' )  order by p.id desc")
    Page<ParkingPrice> findByNameLike(Long idCompany,String search, Pageable pageable);

    List<ParkingPrice> findAllByVehicleTypeId(Long vehicleType_id);
}
