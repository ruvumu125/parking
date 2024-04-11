package com.parking.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.dto.CompanyDto;
import com.parking.dto.VehicleTypeDto;
import com.parking.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.dto.ParkingSpaceDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.model.ParkingSpace;
import com.parking.repository.ParkingSpaceRepository;
import com.parking.services.ParkingSpaceService;
import com.parking.validator.ParkingSpaceValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    public ParkingSpaceServiceImpl(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @Override
    public ParkingSpaceDto save(ParkingSpaceDto dto) {
        List<String> errors = ParkingSpaceValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Parking Space is not valid", dto);
            throw new InvalidEntityException("La place du parking n'est pas valide", ErrorCodes.PARKINGSPACE_NOT_VALID, errors);
        }

        if(parkingSpaceAlreadyExists(dto.getName(),dto.getCompany().getId())) {
            throw new InvalidEntityException("Une autre place de parking avec le meme nom existe deja", ErrorCodes.PARKINGSPACE_ALREADY_EXISTS,
                    Collections.singletonList("Une autre place de parking avec le meme nom existe deja dans la BDD"));
        }

        return ParkingSpaceDto.fromEntity(
                parkingSpaceRepository.save(ParkingSpaceDto.toEntity(dto))
        );
    }

    private boolean parkingSpaceAlreadyExists(String name,Long company_id) {
        Optional<ParkingSpace> parkingSpace = parkingSpaceRepository.findParkingSpaceByName(name,company_id);
        return parkingSpace.isPresent();
    }

    @Override
    public ParkingSpaceDto findById(Long id) {
        if(id == null) {
            log.error("Parking Space ID is null");
        }

        return parkingSpaceRepository.findById(id)
                .map(ParkingSpaceDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune place de parking avec l'ID =  " +id+ " n'a ete trouvé dans la BDD",
                        ErrorCodes.PARKINGSPACE_NOT_FOUND)
                );
    }

    @Override
    public List<ParkingSpaceDto> findAll() {

        return parkingSpaceRepository.findAll().stream()
                .map(ParkingSpaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ParkingSpaceDto> findByNameLike(Long idCompany, String search, Pageable pageable) {

        Page<ParkingSpace> parkingSpaces;
        if (search != null) {
            parkingSpaces = parkingSpaceRepository.findByNameLike(idCompany,search, pageable);
        } else {
            parkingSpaces = parkingSpaceRepository.findAllParkingSpace(idCompany,pageable);
        }

        return parkingSpaces.map(ParkingSpaceDto::fromEntity);
    }

    @Override
    public List<ParkingSpaceDto> findCompanyParkingSpaces(Long idCompany) {

        return parkingSpaceRepository.findCompanyParkingSpaces(idCompany).stream()
                .map(ParkingSpaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(id == null) {
            log.error("Parking Space ID is null");
        }

        parkingSpaceRepository.deleteById(id);
    }

}
