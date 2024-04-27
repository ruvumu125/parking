package com.parking.services.impl;

import com.parking.dto.VehiculeAccountDto;
import com.parking.dto.VehicleDto;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.model.Vehicle;
import com.parking.repository.VehiculeAccountRepository;
import com.parking.repository.VehicleRepository;
import com.parking.services.QRCodeService;
import com.parking.services.VehicleService;
import com.parking.validator.VehicleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.time.Instant;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehiculeAccountRepository accountRepository;
    private final QRCodeService qrCodeService;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehiculeAccountRepository accountRepository, QRCodeService qrCodeService) {
        this.vehicleRepository = vehicleRepository;
        this.accountRepository = accountRepository;
        this.qrCodeService = qrCodeService;
    }


    @Override
    public VehicleDto save(VehicleDto dto) {

        List<String> errors = VehicleValidator.validate(dto);
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le vehicule n'est pas valide", ErrorCodes.VEHICLE_NOT_VALID,errors);
        }

        if ((dto.getId() ==null || dto.getId().compareTo(0L) == 0)){

            if(vehicleAlreadyExists(dto.getRegistrationNumber())) {
                throw new InvalidEntityException("Un autre vehicule avec le meme numero d'immatriculation existe deja", ErrorCodes.VEHICLE_ALREADY_EXISTS,
                        Collections.singletonList("Un autre vehicule avec le meme numero d'immatriculation existe deja dans la BDD"));
            }
            VehicleDto savedVehicule = VehicleDto.fromEntity(
                    vehicleRepository.save(VehicleDto.toEntity(dto))
            );

            VehiculeAccountDto accountDto = fromVehicule(savedVehicule,generateAccountNumber(10));
            accountRepository.save(VehiculeAccountDto.toEntity(accountDto));

            return savedVehicule;

        }

        Vehicle existingVehicle=vehicleRepository.findVehicleById(dto.getId());
        if (existingVehicle !=null && !existingVehicle.getRegistrationNumber().equals(dto.getRegistrationNumber())){

            if(vehicleAlreadyExists(dto.getRegistrationNumber())) {
                throw new InvalidEntityException("Un autre vehicule avec le meme numero d'immatriculation existe deja", ErrorCodes.VEHICLE_ALREADY_EXISTS,
                        Collections.singletonList("Un autre vehicule avec le meme numero d'immatriculation existe deja dans la BDD"));
            }

        }


        return VehicleDto.fromEntity(
                vehicleRepository.save(VehicleDto.toEntity(dto))
        );
    }

    private VehiculeAccountDto fromVehicule(VehicleDto dto, String numero_compte) {
        String qrCodeText=numero_compte+generateUniqueId();
        byte[] qrCodeImage = null;
        try {
            qrCodeImage = qrCodeService.generateQRCodeImage(qrCodeText, 300, 300);
        } catch (Exception e) {
            // Handle exception
        }
        return VehiculeAccountDto.builder()
                .accountNumber(numero_compte)
                .vehicle(dto)
                .qrCodeImage(qrCodeImage)
                .qrCodeString(qrCodeText)
                .openDate(Instant.now())
                .build();
    }

    public static String generateAccountNumber(int length) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    public static String generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        return uuid.toString() + "-" + timestamp;
    }

    private boolean vehicleAlreadyExists(String registrationNumber) {
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByRegistrationNumber(registrationNumber);
        return vehicle.isPresent();
    }

    @Override
    public VehicleDto findById(Long id) {
        return null;
    }

    @Override
    public Page<VehicleDto> findByVehicleRegistrationNumber(String search, Pageable pageable) {

        Page<Vehicle> vehicles;
        if (search != null) {
            vehicles = vehicleRepository.findByVehiculeRegistrationNumberLike(search, pageable);
        } else {
            // If no category is provided, fetch all products
            vehicles = vehicleRepository.findAllVehicle(pageable);
        }
        return vehicles.map(VehicleDto::fromEntity);
    }

    @Override
    public void delete(Long id) {

    }
}
