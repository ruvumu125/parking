package com.parking.dto;

import com.parking.model.User;
import com.parking.model.UserRoleEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	
	private Long id;
	private String userFullName;
	private String userEmail;
	private String userPassword;
	private UserRoleEnum userRoleEnum;
	private String userPhoneNumber;
	private Boolean isUserActive;

	public static UserDto fromEntity(User user) {
		if(user == null) {
			return null;
		}
		
		return UserDto.builder()
				.id(user.getId())
				.userFullName(user.getUserFullName())
				.userEmail(user.getUserEmail())
				.userPassword(user.getUserPassword())
				.userRoleEnum(user.getUserRoleEnum())
				.userPhoneNumber(user.getUserPhoneNumber())
				.isUserActive(user.getIsUserActive())
				.build();
	}
	
	public static User toEntity(UserDto userDto) {
		if(userDto == null) {
			return null;
		}
		
		User user = new User();
		user.setId(userDto.getId());
		user.setUserFullName(userDto.getUserFullName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserRoleEnum(userDto.getUserRoleEnum());
		user.setUserPhoneNumber(userDto.getUserPhoneNumber());
		user.setIsUserActive(userDto.getIsUserActive());
		
		return user;
	}
}
