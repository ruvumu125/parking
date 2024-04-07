package com.parking.controller.api;

import java.util.List;

import com.parking.dto.VehicleTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.SuperadminDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/superadmins")
public interface SuperadminApi {
	
	@Operation(summary = "Créer un superadmin", description = "Cette methode permet d'enregistrer ou modifier un superadmin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet superadmin cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet superadmin n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/superadmins/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	SuperadminDto save(@RequestBody SuperadminDto dto);

    @Operation(summary = "Trouver un superadmin par son ID", description = "Cette methode permet de chercher un superamdin par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le superadmin a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune superadmin n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/superadmins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    SuperadminDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les superadmins", description = "Cette methode permet de chercher et renvoyer la liste des superadmins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des superadmins / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/superadmins/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<SuperadminDto> findByNameEmailPhoneLike(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Supprimer un superadmin par son ID", description = "Cette methode permet de supprimer un superadmin par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le superadmin a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/superadmins/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
