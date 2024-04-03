package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.AdminTypeEnum;
import com.parking.model.Company;
import com.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	List<Admin> findAllById(Long id);

	@Query(value = "select u from Admin u where u.company.id = :companyId AND u.adminTypeEnum = 'MAIN_ADMIN'")
	Optional<Admin> findMainAdminOfGivenCompany(@Param("companyId") Long companyId);
	// JPQL query

	@Query(value = "select u from Admin u where u.id = :id")
	Admin findAdminById(@Param("id") Long id);

	@Query(value = "select a from Admin a join User u on a.user.id = u.id where a.adminTypeEnum = 'MAIN_ADMIN' ")
	List<Admin> findMainAdmins();



}
