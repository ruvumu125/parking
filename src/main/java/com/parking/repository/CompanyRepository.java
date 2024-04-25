package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.Company;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	// JPQL query
	@Query(value = "select c from Company c where c.id = :id")
	Company findCompanyById(@Param("id") Long id);

	@Query(value = "select c from Company c where c.companyName = :name")
	Optional<Company> findCompanyByName(@Param("name") String name);
	
	@Query(value = "select c from Company c where c.companyPhoneNumber = :phoneNumber")
	Optional<Company> findCompanyByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	@Query(value = "select c from Company c  order by c.id desc ")
	Page<Company> findAllCompanies(Pageable pageable);

	@Query(value = "select c from Company c  where  UPPER(c.companyName) like CONCAT('%',UPPER(?1),'%' ) OR  UPPER(c.companyPhoneNumber) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(c.companyAddress) like CONCAT('%',UPPER(?1),'%' )  order by c.id desc ")
	Page<Company> findByNamePhoneAdressAdminLike(String search, Pageable pageable);

	@Query(" SELECT c FROM Company c " +
			" WHERE c.id NOT IN (" +
			"  SELECT adm.company.id FROM Admin adm " +
			"  WHERE adm.adminTypeEnum= 'MAIN_ADMIN'" +
			")")
	List<Company> findCompaniesWithNoMainAdmin();
}
