package com.parking.repository;

import java.util.List;
import java.util.Optional;


import com.parking.model.Admin;
import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Superadmin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperadminRepository extends JpaRepository<Superadmin, Long> {

	@Query(value = "select s from Superadmin s order by s.id desc ")
	Page<Superadmin> findAllSuperAdmins(Pageable pageable);

	@Query(value = "select s from Superadmin s join User u on s.user.id = u.id where UPPER(u.userFullName) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?1),'%' ) order by s.id desc ")
	Page<Superadmin> findByNameEmailPhoneLike(String search, Pageable pageable);

	@Query(value = "select u from Superadmin u where u.superadminTypeEnum = 'MAIN_SUPERADMIN'")
	Optional<Superadmin> findMainSuperAdmin();

	@Query(value = "select s from Superadmin s where s.id = :id")
	Superadmin findSuperAdminById(@Param("id") Long id);

	
}
