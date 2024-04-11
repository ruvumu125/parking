package com.parking.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.UserDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/users")
public interface UserApi {

    @Operation(summary = "Trouver un utilisateur par son ID", description = "Cette methode permet de chercher un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les utilisateurs", description = "Cette methode permet de chercher et renvoyer la liste des utilisateurs qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des utilisateurs / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAll();

    @Operation(summary = "Activer un utilisateur par son ID", description = "Cette methode permet d'activer un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur a ete active")
    })
    @PutMapping(value = Constants.APP_ROOT + "/users/enable/{id}")
    void enableUser(@PathVariable("id") Long id);

    @Operation(summary = "Desactiver un utilisateur par son ID", description = "Cette methode permet desactiver un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur a ete deactivee")
    })
    @PutMapping(value = Constants.APP_ROOT + "/users/desable/{id}")
    void desableUser(@PathVariable("id") Long id);
}
