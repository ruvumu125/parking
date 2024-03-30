package com.parking.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parking.dto.AgentDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/agents")
public interface AgentApi {
	
	@Operation(summary = "Créer un agent", description = "Cette methode permet d'enregistrer ou modifier un agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet agent cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet agent n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/agents/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	AgentDto save(@RequestBody AgentDto dto);

    @Operation(summary = "Trouver un agent par son ID", description = "Cette methode permet de chercher un agent par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'agent a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun agent n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/agents/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AgentDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les agents", description = "Cette methode permet de chercher et renvoyer la liste des agents qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des agents / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/agents/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AgentDto> findAll();

    @Operation(summary = "Supprimer un agent par son ID", description = "Cette methode permet de supprimer un agent par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'agent a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/agents/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
