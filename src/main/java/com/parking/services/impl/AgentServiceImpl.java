package com.parking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.AgentDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.AgentRepository;
import com.parking.services.AgentService;
import com.parking.validator.AgentValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {
	
	private final AgentRepository agentRepository;
	
	@Autowired
	public AgentServiceImpl(AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}

	@Override
	public AgentDto save(AgentDto dto) {
		List<String> errors = AgentValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Agent is not valid {}", dto);
			throw new InvalidEntityException("L'agent n'est pas valide", ErrorCodes.AGENT_NOT_VALID, errors);
		}
		
		return AgentDto.fromEntity(
				agentRepository.save(AgentDto.toEntity(dto))
		);
	}

	@Override
	public AgentDto findById(Long id) {
		if(id == null) {
			log.error("Agent ID is null");
		}
		return agentRepository.findById(id)
				.map(AgentDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun agent avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.AGENT_NOT_FOUND));
	}

	@Override
	public List<AgentDto> findAll() {
		
		return agentRepository.findAll().stream()
				.map(AgentDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Agent ID is null");
		}

		agentRepository.deleteById(id);
		
	}
}
