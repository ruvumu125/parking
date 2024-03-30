package com.parking.repository;

import java.util.List;
import java.util.Optional;


import com.parking.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Superadmin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperadminRepository extends JpaRepository<Superadmin, Long> {

	List<Superadmin> findAllById(Long id);

	@Query(value = "select u from Superadmin u where u.superadminTypeEnum = 'MAIN_SUPERADMIN'")
	Optional<Superadmin> findMainSuperAdmin();

	@Query(value = "select s from Superadmin s where s.id = :id")
	Superadmin findSuperAdminById(@Param("id") Long id);

	
}
