package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

	@Query(value = "select v from VehicleType v where v.id = :id")
	VehicleType findVehicleTypeById(@Param("id") Long id);

	@Query(value = "select v from VehicleType v order by v.id desc ")
	Page<VehicleType> findAllVehiculeType(Pageable pageable);

	//JPQL query
	@Query(value = "select v from VehicleType v where v.vehiculeTypeName = :name")
	Optional<VehicleType> findVehicleTypeByName(@Param("name") String name);

	@Query(value = "select v from VehicleType v where UPPER(v.vehiculeTypeName) like CONCAT('%',UPPER(?1),'%' ) order by v.id desc ")
	Page<VehicleType> findByVehiculeTypeNameLike(String search, Pageable pageable);

	@Query(" SELECT v FROM VehicleType v " +
			" WHERE v.id NOT IN (" +
			"  SELECT pp.vehicleType.id FROM ParkingPrice pp " +
			"  WHERE pp.company.id=?1" +
			")")
	List<VehicleType> findAvailableVehiculeTypeGivenCompany(Long idCompany);

}
