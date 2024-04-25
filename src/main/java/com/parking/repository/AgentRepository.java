package com.parking.repository;

import java.util.List;

import com.parking.model.Admin;
import com.parking.model.ParkingSpace;
import com.parking.model.Superadmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findAllById(Long id);
	// JPQL query

	@Query(value = "select a from Agent a where a.id = :id")
	Agent findAgentById(@Param("id") Long id);

	@Query(value = "select ag from Agent ag where ag.company.id=?1 order by ag.id desc ")
	Page<Agent> findAllCompanyAgents(Long idCompany,Pageable pageable);

	@Query(value = "select ag from Agent ag join User u on ag.user.id = u.id where ag.company.id=?1 AND UPPER(u.userFullName) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?2),'%' ) order by ag.id desc ")
	Page<Agent> findByNameEmailPhoneLike(Long idCompany,String search, Pageable pageable);

	List<Agent> findAllByParkingSpaceId(Long parkingSpace_id);
}
