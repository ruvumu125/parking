package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.exceptions.InvalidOperationException;
import com.parking.model.ParkingPrice;
import com.parking.repository.ParkingPriceRepository;
import com.parking.repository.ParkingTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.dto.VehicleTypeDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.model.VehicleType;
import com.parking.repository.VehicleTypeRepository;
import com.parking.services.VehicleTypeService;
import com.parking.validator.VehicleTypeValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleTypeServiceImpl implements VehicleTypeService {
	
	private final VehicleTypeRepository vehicleTypeRepository;
	private final ParkingPriceRepository parkingPriceRepository;
	
	@Autowired
	public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository, ParkingPriceRepository parkingPriceRepository) {
		this.vehicleTypeRepository = vehicleTypeRepository;
		this.parkingPriceRepository = parkingPriceRepository;
	}
	
	@Override
	public VehicleTypeDto save(VehicleTypeDto dto) {
		List<String> errors = VehicleTypeValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Vehicle type is not valid {}", dto);
			throw new InvalidEntityException("Le vehicule n'est pas valide", ErrorCodes.VEHICLETYPE_NOT_VALID, errors);
		}

		if ((dto.getId() ==null || dto.getId().compareTo(0L) == 0)){

			if(vehicleTypeAlreadyExists(dto.getVehiculeTypeName())) {
				throw new InvalidEntityException("Un autre type de vehicule avec le meme nom existe deja", ErrorCodes.VEHICLETYPE_ALREADY_EXISTS,
						Collections.singletonList("Un autre type de vehicule avec le meme nom existe deja dans la BDD"));
			}
			return VehicleTypeDto.fromEntity(
					vehicleTypeRepository.save(VehicleTypeDto.toEntity(dto))
			);
		}

		VehicleType existingVehicleType=vehicleTypeRepository.findVehicleTypeById(dto.getId());
		if (existingVehicleType !=null && !existingVehicleType.getVehiculeTypeName().equals(dto.getVehiculeTypeName())){

			if(vehicleTypeAlreadyExists(dto.getVehiculeTypeName())) {
				throw new InvalidEntityException("Un autre type de vehicule avec le meme nom existe deja", ErrorCodes.VEHICLETYPE_ALREADY_EXISTS,
						Collections.singletonList("Un autre type de vehicule avec le meme nom existe deja dans la BDD"));
			}
		}

		return VehicleTypeDto.fromEntity(
				vehicleTypeRepository.save(VehicleTypeDto.toEntity(dto))
		);


	}

	private boolean vehicleTypeAlreadyExists(String name) {
		Optional<VehicleType> vehicleType = vehicleTypeRepository.findVehicleTypeByName(name);
		return vehicleType.isPresent();
	}

	@Override
	public VehicleTypeDto findById(Long id) {
		
		if(id == null) {
			log.error("Vehicle type is null");
		}
		return vehicleTypeRepository.findById(id)
				.map(VehicleTypeDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun type de vehicle avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.VEHICLETYPE_NOT_FOUND)
						);
	}

	@Override
	public Page<VehicleTypeDto> findByVehiculeTypeName(String search, Pageable pageable) {

		Page<VehicleType> vehicleTypes;
		if (search != null) {
			vehicleTypes = vehicleTypeRepository.findByVehiculeTypeNameLike(search, pageable);
		} else {
			// If no category is provided, fetch all products
			vehicleTypes = vehicleTypeRepository.findAllVehiculeType(pageable);
		}

		return vehicleTypes.map(VehicleTypeDto::fromEntity);
	}

	@Override
	public List<VehicleTypeDto> findAvailableVehiculeTypeGivenCompany(Long idCompany) {

		return vehicleTypeRepository.findAvailableVehiculeTypeGivenCompany(idCompany).stream()
				.map(VehicleTypeDto::fromEntity)
				.collect(Collectors.toList());
	}


	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Vehicle type ID is null");
		}

		List<ParkingPrice> parkingPrices = parkingPriceRepository.findAllByVehicleTypeId(id);
		if(!parkingPrices.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer ce type de vehicule qui est deja utilise",
					ErrorCodes.VEHICLETYPE_IN_USE);
		}
		
		vehicleTypeRepository.deleteById(id);
		
	}

}
