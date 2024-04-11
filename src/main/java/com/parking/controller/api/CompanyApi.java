package com.parking.controller.api;
import com.parking.dto.CompanyListDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.CompanyDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RequestMapping("/companies")
public interface CompanyApi {
	
	@Operation(summary = "Créer une entreprise", description = "Cette methode permet d'enregistrer ou modifier une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet entreprise cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet entreprise n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/companies/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto save(@RequestBody CompanyDto dto);

    @Operation(summary = "Trouver une entreprise par son ID", description = "Cette methode permet de chercher une entreprise par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'entreprise a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune entreprise n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/companies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les entreprises", description = "Cette methode permet de chercher et renvoyer la liste des entreprises qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des entreprises / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/companies/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<CompanyListDto> findAllCompanies(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Récupérer la liste de tous les entreprises n'ayant pas d'admins", description = "Cette methode permet de chercher et renvoyer la liste des entreprises qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des entreprises / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/companies/companies-with-no-main-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDto> findCompaniesWithNoMainAdmin();

    @Operation(summary = "Supprimer une entreprise par son ID", description = "Cette methode permet de supprimer une entreprise par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'entreprise a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/companies/delete/{id}")
    void delete(@PathVariable("id") Long id);

    @Operation(summary = "Activer une entreprise par son ID", description = "Cette methode permet d'activer une entreprise par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'entreprise a ete active")
    })
    @PutMapping(value = Constants.APP_ROOT + "/companies/enable/{id}")
    void enableCompany(@PathVariable("id") Long id);

    @Operation(summary = "Desactiver une entreprise par son ID", description = "Cette methode permet desactiver une entreprise par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'entreprise a ete deactivee")
    })
    @PutMapping(value = Constants.APP_ROOT + "/companies/desable/{id}")
    void desableCompany(@PathVariable("id") Long id);
}
