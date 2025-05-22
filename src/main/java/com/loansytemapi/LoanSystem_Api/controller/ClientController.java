package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.ClientDTO;
import com.loansytemapi.LoanSystem_Api.dto.ClientResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
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
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = this.iClientService.getAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los clientes de un usuario en especifico", description = "Retorna una lista con todos los clientes registrados que tiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay clientes disponibles")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClientResponseDTO>> getAllClientsByUserId(@PathVariable int userId) throws NotFoundException {
        List<ClientResponseDTO> userClients = this.iClientService.getAllClientsByUserId(userId);
        if (userClients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userClients, HttpStatus.OK);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable @Parameter(description = "ID del cliente a buscar") int id) {
        try {
            ClientResponseDTO client = this.iClientService.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Parameter(description = "Datos del cliente a crear") ClientDTO newClient) throws IncompleteDataException, NotFoundException {
        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientResponseDTO client = this.iClientService.createClient(newClient);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable @Parameter(description = "ID del cliente a actualizar") int id,
            @RequestBody @Parameter(description = "Datos actualizados del cliente") ClientDTO newClient)
            throws IncompleteDataException, NotFoundException {

        if (newClient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientResponseDTO updatedClient = this.iClientService.updateClient(id, newClient);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> deleteClient(@PathVariable @Parameter(description = "ID del cliente a eliminar") int id) throws NotFoundException {
        ClientResponseDTO client = this.iClientService.deleteClient(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Buscar clientes", description = "Filtra los clientes en base a una consulta de búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron coincidencias")
    })
    @GetMapping("/search/{userId}")
    public ResponseEntity<List<ClientResponseDTO>> searchClients(@PathVariable int userId,
            @RequestParam(required = false) @Parameter(description = "Texto de búsqueda para filtrar clientes") String query) {
        List<ClientResponseDTO> clients = this.iClientService.searchClientsByQuery(userId, query);
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}