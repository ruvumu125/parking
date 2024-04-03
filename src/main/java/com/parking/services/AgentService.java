package com.parking.services;

import java.util.List;

import com.parking.dto.AgentDto;
import com.parking.dto.ParkingSpaceDto;

public interface AgentService {
	
	AgentDto save(AgentDto dto);
	
	AgentDto findById(Long id);
	
	List<AgentDto> findAll();

	List<AgentDto> findCompanyAgents(Long idCompany);
	
	void delete(Long id);
}
