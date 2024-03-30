package com.parking.dto;

import com.parking.model.Superadmin;
import com.parking.model.SuperadminTypeEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuperadminDto {
	
	private Long id;
	private UserDto user;
	private SuperadminTypeEnum superadminTypeEnum;
	
	public static SuperadminDto fromEntity(Superadmin superadmin) {
		if(superadmin == null) {
			return null;
		}
		
		return SuperadminDto.builder()
				.id(superadmin.getId())
				.user(UserDto.fromEntity(superadmin.getUser()))
				.superadminTypeEnum(superadmin.getSuperadminTypeEnum())
				.build();
	}
	
	public static Superadmin toEntity(SuperadminDto superadminDto) {
		if(superadminDto == null) {
			return null;
		}
		
		Superadmin superadmin = new Superadmin();
		superadmin.setId(superadminDto.getId());
		superadmin.setUser(UserDto.toEntity(superadminDto.getUser()));
		superadmin.setSuperadminTypeEnum(superadminDto.getSuperadminTypeEnum());
		
		return superadmin;
	}
}
