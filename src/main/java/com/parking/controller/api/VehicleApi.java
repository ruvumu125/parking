package com.parking.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.dto.VehicleDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/vehicles")
public interface VehicleApi {

    @Operation(summary = "Créer un vehicule", description = "Cette methode permet d'enregistrer ou modifier un vehicule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet vehicule cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet vehicule n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/vehicles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleDto save(@RequestBody VehicleDto dto);

    @Operation(summary = "Trouver un vehicule par son ID", description = "Cette methode permet de chercher un vehicule par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le vehicule a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun vehicule n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les vehicules", description = "Cette methode permet de chercher et renvoyer la liste des vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des vehicules / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VehicleDto> findByVehiculeRegistrationNumber(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Supprimer un vehicule par son ID", description = "Cette methode permet de supprimer un vehicule par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le vehicule a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/vehicles/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
