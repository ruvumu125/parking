package com.parking.services;

import java.util.List;

import com.parking.dto.AdminDto;
import com.parking.dto.UserDto;

public interface UserService {
	
	UserDto findById(Long id);
	
	List<UserDto> findAll();

	void enableUser(Long userId);

	void desableUser(Long userId);
}
