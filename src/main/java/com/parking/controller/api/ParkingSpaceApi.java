package com.parking.controller.api;

import java.util.List;

import com.parking.dto.VehicleTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.ParkingSpaceDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parking_spaces")
public interface ParkingSpaceApi {

    @Operation(summary = "Créer une place de parking", description = "Cette methode permet d'enregistrer ou modifier une place de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet place de parking cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet place de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parking_spaces/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingSpaceDto save(@RequestBody ParkingSpaceDto dto);

    @Operation(summary = "Trouver une place de parking par son ID", description = "Cette methode permet de chercher une place de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune place de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_spaces/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingSpaceDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les places de parking", description = "Cette methode permet de chercher et renvoyer la liste des places de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des places de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_spaces/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingSpaceDto> findAll();

    @Operation(summary = "Récupérer la liste de places de parking d'une entreprise", description = "Cette methode permet de chercher et renvoyer la liste des places de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des places de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_spaces/company/{idCompany}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ParkingSpaceDto> findByVehiculeTypeName(
            @PathVariable("idCompany") Long idCompany,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Récupérer la liste de places de parking d'une entreprise", description = "Cette methode permet de chercher et renvoyer la liste des places de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des places de parking d'une entreprise / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_spaces/company-spaces/{idCompany}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingSpaceDto> findCompanyParkingSpaces(@PathVariable("idCompany") Long idCompany);

    @Operation(summary = "Supprimer une place de parking par son ID", description = "Cette methode permet de supprimer une place de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parking_spaces/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
