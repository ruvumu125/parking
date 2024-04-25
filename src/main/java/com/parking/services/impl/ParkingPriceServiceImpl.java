package com.parking.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.dto.ParkingSpaceDto;
import com.parking.model.ParkingPrice;
import com.parking.model.ParkingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.dto.ParkingPriceDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.ParkingPriceRepository;
import com.parking.services.ParkingPriceService;
import com.parking.validator.ParkingPriceValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingPriceServiceImpl implements ParkingPriceService {

    private final ParkingPriceRepository parkingPriceRepository;

    @Autowired
    public ParkingPriceServiceImpl(ParkingPriceRepository parkingPriceRepository) {
        this.parkingPriceRepository = parkingPriceRepository;
    }

    @Override
    public ParkingPriceDto save(ParkingPriceDto dto) {
        List<String> errors = ParkingPriceValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Parking Price is not valid", dto);
            throw new InvalidEntityException("Le prix du parking n'est pas valide", ErrorCodes.PARKINGPRICE_NOT_VALID, errors);
        }

        if (dto.getId() ==null || dto.getId().compareTo(0L) == 0){

            if(parkingPriceAlreadyExists(dto.getCompanyDto().getId(),dto.getVehicleTypeDto().getId())) {
                throw new InvalidEntityException("Le typeaa de vehicule renseigne a deja un prix", ErrorCodes.PARKINGPRICE_ALREADY_EXISTS,
                        Collections.singletonList("Le typeaa de vehicule renseigne a deja un prix"));
            }

            return ParkingPriceDto.fromEntity(
                    parkingPriceRepository.save(ParkingPriceDto.toEntity(dto))
            );
        }

        ParkingPrice existingParkingPrice=parkingPriceRepository.findParkingPriceById(dto.getId(),dto.getCompanyDto().getId());
        if (existingParkingPrice !=null && !existingParkingPrice.getVehicleType().getId().equals(dto.getVehicleTypeDto().getId())){

            if(parkingPriceAlreadyExists(dto.getCompanyDto().getId(),dto.getVehicleTypeDto().getId())) {
                throw new InvalidEntityException("Le type de vehicule renseigne a deja un prix", ErrorCodes.PARKINGPRICE_ALREADY_EXISTS,
                        Collections.singletonList("Le type de vehicule renseigne a deja un prix"));
            }
        }

        return ParkingPriceDto.fromEntity(
                parkingPriceRepository.save(ParkingPriceDto.toEntity(dto))
        );
    }

    private boolean parkingPriceAlreadyExists(Long company_id,Long type_vehicule_id) {
        Optional<ParkingPrice> parkingPrice = parkingPriceRepository.findParkingPriceByCompanyAndVehiculeType(company_id,type_vehicule_id);
        return parkingPrice.isPresent();
    }

    @Override
    public ParkingPriceDto findById(Long id) {
        if(id == null) {
            log.error("Parking Price ID is not null");
        }
        return parkingPriceRepository.findById(id)
                .map(ParkingPriceDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun prix de parking avec l'ID = " +id+ " n'a ete trouvé dans la BDD",
                        ErrorCodes.PARKINGPRICE_NOT_FOUND)
                );
    }

    @Override
    public List<ParkingPriceDto> findAll() {

        return parkingPriceRepository.findAll().stream()
                .map(ParkingPriceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ParkingPriceDto> findByNameLike(Long idCompany, String search, Pageable pageable) {

        Page<ParkingPrice> parkingPrices;
        if (search != null) {
            parkingPrices = parkingPriceRepository.findByNameLike(idCompany,search, pageable);
        } else {
            parkingPrices = parkingPriceRepository.findCompanyParkingPrices(idCompany,pageable);
        }

        return parkingPrices.map(ParkingPriceDto::fromEntity);
    }


    @Override
    public void delete(Long id) {
        if(id == null) {
            log.error("Parking Price ID is null");
        }

        parkingPriceRepository.deleteById(id);
    }

}
