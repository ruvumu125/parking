package com.parking.services.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.model.Company;
import com.parking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.UserDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.repository.UserRepository;
import com.parking.services.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDto findById(Long id) {
		
		if(id == null) {
			log.error("User ID is null");
		}

		return userRepository.findById(id)
				.map(UserDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun utilisateur avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.USER_NOT_FOUND)
				);
	}

	@Override
	public List<UserDto> findAll() {
		
		return userRepository.findAll().stream()
				.map(UserDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void enableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(true);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}

	@Override
	@Transactional
	public void desableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(false);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}
}
