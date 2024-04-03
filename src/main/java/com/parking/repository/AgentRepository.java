package com.parking.repository;

import java.util.List;

import com.parking.model.Admin;
import com.parking.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findAllById(Long id);
	// JPQL query

	@Query(value = "select a from Agent a where a.id = :id")
	Agent findAgentById(@Param("id") Long id);

	@Query("select ag from  Agent ag where ag.company.id= :idCompany")
	List<Agent> findCompanyAgents(@Param("idCompany") Long idCompany);
}
