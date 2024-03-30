package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
	List<VehicleType> findAllById(Long id);
	//JPQL query
	@Query(value = "select v from VehicleType v where v.vehiculeTypeName = :name")
	Optional<VehicleType> findVehicleTypeByName(@Param("name") String name);
}
