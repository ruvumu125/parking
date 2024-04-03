package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.AgentApi;
import com.parking.dto.AgentDto;
import com.parking.services.AgentService;

@RestController
public class AgentController implements AgentApi {

	private final AgentService agentService;

	@Autowired
	public AgentController(AgentService agentService) {
		this.agentService = agentService;
	}

	@Override
	public AgentDto save(AgentDto dto) {

		return agentService.save(dto);
	}

	@Override
	public AgentDto findById(Long id) {

		return agentService.findById(id);
	}

	@Override
	public List<AgentDto> findAll() {

		return agentService.findAll();
	}

	@Override
	public List<AgentDto> findCompanyAgents(Long idCompany) {
		return agentService.findCompanyAgents(idCompany);
	}

	@Override
	public void delete(Long id) {
		agentService.delete(id);

	}

}
