package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	List<Company> findAllById(Long id);
	// JPQL query
	@Query(value = "select c from Company c where c.companyName = :name")
	Optional<Company> findCompanyByName(@Param("name") String name);
	
	@Query(value = "select c from Company c where c.companyPhoneNumber = :phoneNumber")
	Optional<Company> findCompanyByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
