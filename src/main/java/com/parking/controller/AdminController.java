package com.parking.controller;

import java.util.List;

import com.parking.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<AdminDto> findMainAdmins(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return adminService.findAllMainAdmins(search,pageable);
	}

	@Override
	public Page<AdminDto> findCompanyAdmins(Long idCompany, String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return adminService.findCompanyAdmins(idCompany,search,pageable);
	}

	@Override
	public void delete(Long id) {
		adminService.delete(id);
		
	}

}
