package com.parking.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.ParkingTicketDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parking_tickets")
public interface ParkingTicketApi {

    @Operation(summary = "Créer ou faire entrer un ticket de parking", description = "Cette methode permet d'enregistrer un ticket de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet ticket de parking cree"),
            @ApiResponse(responseCode = "400", description = "L'objet ticket de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parking_tickets/entry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingTicketDto entryParking(@RequestBody ParkingTicketDto dto);

    @Operation(summary = "Faire sortir un ticket de parking", description = "Cette methode permet de modifier un ticket de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet ticket de parking modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet ticket de parking n'est pas valide")
    })
    @PutMapping(value = Constants.APP_ROOT + "/parking_tickets/exit/{id}")
    ParkingTicketDto exitParking(@PathVariable("id") Long parkingTicketId);

    @Operation(summary = "Trouver un ticket de parking par son ID", description = "Cette methode permet de chercher un ticket de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Un ticket de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun ticket de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingTicketDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les tickets de parking", description = "Cette methode permet de chercher et renvoyer la liste des tickets de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des tickets de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tickets/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ParkingTicketDto> findAllParkingTicket(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "Supprimer un ticket de parking par son ID", description = "Cette methode permet de supprimer un ticket de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le ticket de parking a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parking_tickets/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
