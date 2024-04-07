package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<AgentDto> findCompanyAgents(Long idCompany, String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return agentService.findCompanyAgents(idCompany,search,pageable);
	}

	@Override
	public void delete(Long id) {
		agentService.delete(id);

	}

}
