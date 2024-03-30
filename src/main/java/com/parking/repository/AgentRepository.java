package com.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findAllById(Long id);
	// JPQL query
}
