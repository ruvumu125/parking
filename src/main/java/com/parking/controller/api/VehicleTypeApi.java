package com.parking.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.VehicleTypeDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/vehicle_types")
public interface VehicleTypeApi {
	
	@Operation(summary = "Créer un type vehicule", description = "Cette methode permet d'enregistrer ou modifier un type vehicule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet type vehicule cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet type vehicule n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/vehicle_types/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleTypeDto save(@RequestBody VehicleTypeDto dto);

    @Operation(summary = "Trouver un type vehicule par son ID", description = "Cette methode permet de chercher un type vehicule par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le type vehicule a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun type vehicule n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleTypeDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les types vehicules", description = "Cette methode permet de chercher et renvoyer la liste des types vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des types vehicules / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VehicleTypeDto> findByVehiculeTypeName(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Récupérer la liste de tous les types vehicules non encore assigne un prix par une entreprise donnee", description = "Cette methode permet de chercher et renvoyer la liste des types vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste de tous les types vehicules non encore assigne un prix par une entreprise donnee / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/company/{idCompany}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VehicleTypeDto> findAvailableVehiculeTypeGivenCompany(@PathVariable("idCompany") Long idCompany);

    @Operation(summary = "Supprimer un type vehicule par son ID", description = "Cette methode permet de supprimer un type vehicule par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le type vehicule a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/vehicle_types/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
