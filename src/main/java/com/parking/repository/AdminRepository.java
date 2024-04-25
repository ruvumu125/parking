package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	List<Admin> findAllById(Long id);

	@Query(value = "select u from Admin u where u.company.id = :companyId AND u.adminTypeEnum = 'MAIN_ADMIN'")
	Optional<Admin> findMainAdminOfGivenCompany(@Param("companyId") Long companyId);
	// JPQL query

	@Query(value = "select u from Admin u where u.id = :id")
	Admin findAdminById(@Param("id") Long id);

	@Query(value = "select ad from Admin ad where ad.adminTypeEnum = 'MAIN_ADMIN' order by ad.id desc ")
	Page<Admin> findAllMainAdmins(Pageable pageable);

	@Query(value = "select ad from Admin ad join User u on ad.user.id = u.id where ad.adminTypeEnum = 'MAIN_ADMIN' AND UPPER(u.userFullName) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?1),'%' ) OR (UPPER(ad.company.companyName) like CONCAT('%',UPPER(?1),'%' ) AND ad.adminTypeEnum = 'MAIN_ADMIN' ) order by ad.id desc ")
	Page<Admin> findMainAdminsByNameEmailPhoneLike(String search, Pageable pageable);

	@Query(value = "select ad from Admin ad where ad.company.id=?1 order by ad.id desc ")
	Page<Admin> findCompanyAdmins(Long idCompany,Pageable pageable);

	@Query(value = "select ad from Admin ad join User u on ad.user.id = u.id where ad.company.id=?1 AND UPPER(u.userFullName) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?2),'%' )  order by ad.id desc ")
	Page<Admin> findCompanyAdminsByNameEmailPhoneLike(Long idCompany,String search, Pageable pageable);

	List<Admin> findAllByCompanyId(Long company_id);



}
