package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.ClientDTO;
import com.loansytemapi.LoanSystem_Api.dto.ClientResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
import com.loansytemapi.LoanSystem_Api.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clientes", description = "API para la gestión de clientes")
public class ClientController {

    private final IClientService iClientService;
    private final JwtService jwtService;

    @Autowired
    public ClientController(IClientService iClientService, JwtService jwtService) {
        this.iClientService = iClientService;
        this.jwtService = jwtService;
    }

    // === GET ALL CLIENTS ===
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista con todos los clientes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay clientes disponibles"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(@RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ClientResponseDTO> clients = this.iClientService.getAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    // === GET CLIENTS BY USER ID ===
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener todos los clientes de un usuario en específico", description = "Retorna una lista con todos los clientes registrados que tiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay clientes disponibles"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<ClientResponseDTO>> getAllClientsByUserId(
            @PathVariable int userId,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ClientResponseDTO> userClients = this.iClientService.getAllClientsByUserId(userId);
        if (userClients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userClients, HttpStatus.OK);
    }

    // === GET CLIENT BY ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<ClientResponseDTO> getClientById(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            ClientResponseDTO client = this.iClientService.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // === CREATE CLIENT ===
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<ClientResponseDTO> createClient(
            @RequestBody ClientDTO newClient,
            @RequestHeader("Authorization") String authHeader)
            throws IncompleteDataException, NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientResponseDTO client = this.iClientService.createClient(newClient);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    // === UPDATE CLIENT ===
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable int id,
            @RequestBody ClientDTO newClient,
            @RequestHeader("Authorization") String authHeader)
            throws IncompleteDataException, NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientResponseDTO updatedClient = this.iClientService.updateClient(id, newClient);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    // === DELETE CLIENT ===
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<ClientResponseDTO> deleteClient(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ClientResponseDTO client = this.iClientService.deleteClient(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    // === SEARCH CLIENTS ===
    @GetMapping("/search/{userId}")
    @Operation(summary = "Buscar clientes", description = "Filtra los clientes en base a una consulta de búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron coincidencias"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<ClientResponseDTO>> searchClients(
            @PathVariable int userId,
            @RequestParam(required = false) String query,
            @RequestHeader("Authorization") String authHeader) {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ClientResponseDTO> clients = this.iClientService.searchClientsByQuery(userId, query);
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}