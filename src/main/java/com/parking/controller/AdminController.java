package com.parking.controller;

import java.util.List;

import com.parking.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.AdminApi;
import com.parking.dto.AdminDto;
import com.parking.services.AdminService;

@RestController
public class AdminController implements AdminApi {
	
	private final AdminService adminService;
	
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@Override
	public AdminDto save(AdminDto dto) {
		
		return adminService.save(dto);
	}

	@Override
	public AdminDto findById(Long id) {
		
		return adminService.findById(id);
	}

	@Override
	public List<AdminDto> findAll() {
		
		return adminService.findAll();
	}

	@Override
	public void delete(Long id) {
		adminService.delete(id);
		
	}

}
