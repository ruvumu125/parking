package com.parking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.model.Admin;
import com.parking.model.Agent;
import com.parking.model.Company;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyDto {
	
	private Long id;
	private String companyName;
	private String companyPhoneNumber;
	private String companyAddress;
	private Boolean isCompanyActive;

	@JsonIgnore
	private List<Agent> agents;
	@JsonIgnore
	private List<Agent> admins;
	
	public static CompanyDto fromEntity(Company company) {
		if(company == null) {
			return null;
		}
		
		return CompanyDto.builder()
				.id(company.getId())
				.companyName(company.getCompanyName())
				.companyPhoneNumber(company.getCompanyPhoneNumber())
				.companyAddress(company.getCompanyAddress())
				.isCompanyActive(company.getIsCompanyActive())
				.build();
	}
	
	public static Company toEntity(CompanyDto companyDto) {
		if(companyDto == null) {
			return null;
		}
		
		Company company = new Company();
		company.setId(companyDto.getId());
		company.setCompanyName(companyDto.getCompanyName());
		company.setCompanyPhoneNumber(companyDto.getCompanyPhoneNumber());
		company.setCompanyAddress(companyDto.getCompanyAddress());
		company.setIsCompanyActive(companyDto.getIsCompanyActive());
		
		return company;
	}
}
