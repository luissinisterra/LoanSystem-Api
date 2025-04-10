package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.model.Client;
import com.loansytemapi.LoanSystem_Api.service.imp.IClientService;
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

    @Autowired
    public ClientController(IClientService iClientService) {
        this.iClientService = iClientService;
    }

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista con todos los clientes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay clientes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = this.iClientService.getAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable @Parameter(description = "ID del cliente a buscar") String id) {
        Client client = this.iClientService.getClientById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "204", description = "El cuerpo de la solicitud está vacío")
    })
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Parameter(description = "Datos del cliente a crear") Client newClient) {
        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Client client = this.iClientService.createClient(newClient);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "204", description = "El cuerpo de la solicitud está vacío")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(
            @PathVariable @Parameter(description = "ID del cliente a actualizar") String id,
            @RequestBody @Parameter(description = "Datos actualizados del cliente") Client newClient) {
        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Client client = this.iClientService.updateClient(id, newClient);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "204", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable @Parameter(description = "ID del cliente a eliminar") String id) {
        Client client = this.iClientService.deleteClient(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Buscar clientes", description = "Filtra los clientes en base a una consulta de búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron coincidencias")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(
            @RequestParam(required = false) @Parameter(description = "Texto de búsqueda para filtrar clientes") String query) {
        List<Client> clients = this.iClientService.searchClientsByQuery(query);
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
