package com.parking.dto;

import com.parking.model.Agent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentDto {
	
	private Long id;
	private UserDto user;
	private CompanyDto company;
	
	public static AgentDto fromEntity(Agent agent) {
		if(agent == null) {
			return null;
		}
		
		return AgentDto.builder()
				.id(agent.getId())
				.user(UserDto.fromEntity(agent.getUser()))
				.company(CompanyDto.fromEntity(agent.getCompany()))
				.build();
	}
	
	public static Agent toEntity(AgentDto agentDto) {
		if(agentDto == null) {
			return null;
		}
		
		Agent agent = new Agent();
		agent.setId(agentDto.getId());
		agent.setUser(UserDto.toEntity(agentDto.getUser()));
		agent.setCompany(CompanyDto.toEntity(agentDto.getCompany()));
		
		return agent;
	}
}
