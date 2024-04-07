package com.parking.controller.api;

import java.util.List;

import com.parking.dto.ParkingSpaceDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.ParkingPriceDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parking_prices")
public interface ParkingPriceApi {

    @Operation(summary = "Créer un prix de parking", description = "Cette methode permet d'enregistrer ou modifier un prix de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet prix de parking cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet prix de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parking_prices/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingPriceDto save(@RequestBody ParkingPriceDto dto);

    @Operation(summary = "Trouver un prix de parking par son ID", description = "Cette methode permet de chercher un prix de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le prix de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun prix de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_prices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingPriceDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les prix de parking", description = "Cette methode permet de chercher et renvoyer la liste des prix de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des prix de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_prices/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingPriceDto> findAll();

    @Operation(summary = "Récupérer la liste de prix de parking d'une entreprise", description = "Cette methode permet de chercher et renvoyer la liste des prix de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des prix de parking d'une entreprise")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_prices/company/{idCompany}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ParkingPriceDto> findCompanyParkingPrices(
            @PathVariable("idCompany") Long idCompany,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Supprimer un prix de parking par son ID", description = "Cette methode permet de supprimer un prix de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le prix de parking a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parking_prices/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
