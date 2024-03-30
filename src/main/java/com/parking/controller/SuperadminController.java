package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.SuperadminApi;
import com.parking.dto.SuperadminDto;
import com.parking.services.SuperadminService;

@RestController
public class SuperadminController implements SuperadminApi {

	private final SuperadminService superadminService;

	@Autowired
	public SuperadminController(SuperadminService superadminService) {
		this.superadminService = superadminService;
	}

	@Override
	public SuperadminDto save(SuperadminDto dto) {
		
		return superadminService.save(dto);
	}

	@Override
	public SuperadminDto findById(Long id) {
		
		return superadminService.findById(id);
	}

	@Override
	public List<SuperadminDto> findAll() {
		
		return superadminService.findAll();
	}

	@Override
	public void delete(Long id) {
		superadminService.delete(id);
		
	}
	
	
}
