package com.parking.controller.api;

import java.util.List;

import com.parking.dto.AgentDto;
import com.parking.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.AdminDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/admins")
public interface AdminApi {
	
	@Operation(summary = "Créer un admin", description = "Cette methode permet d'enregistrer ou modifier un admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet admin cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet admin n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/admins/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AdminDto save(@RequestBody AdminDto dto);

    @Operation(summary = "Trouver un admin par son ID", description = "Cette methode permet de chercher un admin par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'admin a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun admin n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AdminDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les admins", description = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des admins / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AdminDto> findAll();

    @Operation(summary = "Récupérer la liste de tous les admins principaux", description = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des admins principaux / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/main", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<AdminDto> findMainAdmins(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Récupérer la liste de tous les admins d'une entreprise", description = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des admins principaux / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/company/{idCompany}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<AdminDto> findCompanyAdmins(
            @PathVariable("idCompany") Long idCompany,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Supprimer un admin par son ID", description = "Cette methode permet de supprimer un admin par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'admin a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/admins/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
