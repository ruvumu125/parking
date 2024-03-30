package com.parking.dto;

import com.parking.model.Admin;
import com.parking.model.AdminTypeEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDto {
	
	private Long id;
	private UserDto user;
	private AdminTypeEnum adminTypeEnum;
	private CompanyDto company;
	
	public static AdminDto fromEntity(Admin admin) {
		if(admin == null) {
			return null;
		}
		
		return AdminDto.builder()
				.id(admin.getId())
				.user(UserDto.fromEntity(admin.getUser()))
				.adminTypeEnum(admin.getAdminTypeEnum())
				.company(CompanyDto.fromEntity(admin.getCompany()))
				.build();
	}
	
	public static Admin toEntity(AdminDto adminDto) {
		if(adminDto == null) {
			return null;
		}
		
		Admin admin = new Admin();
		admin.setId(adminDto.getId());
		admin.setUser(UserDto.toEntity(adminDto.getUser()));
		admin.setAdminTypeEnum(adminDto.getAdminTypeEnum());
		admin.setCompany(CompanyDto.toEntity(adminDto.getCompany()));
		
		return admin;
	}
}
