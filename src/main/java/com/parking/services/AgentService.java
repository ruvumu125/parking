package com.parking.services;

import java.util.List;

import com.parking.dto.AgentDto;

public interface AgentService {
	
	AgentDto save(AgentDto dto);
	
	AgentDto findById(Long id);
	
	List<AgentDto> findAll();
	
	void delete(Long id);
}
