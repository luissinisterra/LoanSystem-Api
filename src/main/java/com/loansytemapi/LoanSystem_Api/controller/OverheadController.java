package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.CreateOverheadDTO;
import com.loansytemapi.LoanSystem_Api.dto.OverheadResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.service.imp.IOverheadService;
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
@RequestMapping("/api/overheads")
@Tag(name = "Overheads", description = "API for managing user overheads (expenses)")
public class OverheadController {

    private final IOverheadService overheadService;
    private final JwtService jwtService;

    @Autowired
    public OverheadController(IOverheadService overheadService, JwtService jwtService) {
        this.overheadService = overheadService;
        this.jwtService = jwtService;
    }

    // === CREATE OVERHEAD ===
    @PostMapping
    @Operation(summary = "Create a new overhead", description = "Creates a new overhead (expense) for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Overhead created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid overhead data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<OverheadResponseDTO> createOverhead(
            @RequestBody @Parameter(description = "Overhead data to be created") CreateOverheadDTO overhead,
            @RequestHeader("Authorization") String authHeader)
            throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException, NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return new ResponseEntity<>(overheadService.save(overhead), HttpStatus.CREATED);
    }

    // === DELETE OVERHEAD ===
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an overhead", description = "Deletes a user's overhead by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Overhead deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<Void> deleteOverhead(
            @PathVariable @Parameter(description = "ID of the overhead to delete") Integer id,
            @RequestHeader("Authorization") String authHeader)
            throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        overheadService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // === UPDATE OVERHEAD ===
    @PutMapping("/{id}")
    @Operation(summary = "Update an overhead", description = "Updates the details of an existing overhead.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead updated successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<OverheadResponseDTO> updateOverhead(
            @PathVariable Integer id,
            @RequestBody CreateOverheadDTO overhead,
            @RequestHeader("Authorization") String authHeader)
            throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return new ResponseEntity<>(overheadService.update(id, overhead), HttpStatus.OK);
    }

    // === GET ALL OVERHEADS ===
    @GetMapping
    @Operation(summary = "Get all overheads", description = "Retrieves all overheads registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overheads retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<List<OverheadResponseDTO>> getAllOverheads(@RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return new ResponseEntity<>(overheadService.getAll(), HttpStatus.OK);
    }

    // === GET OVERHEAD BY ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Get overhead by ID", description = "Retrieves an overhead by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead found"),
            @ApiResponse(responseCode = "404", description = "Overhead not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<OverheadResponseDTO> getOverheadById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader)
            throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(overheadService.getByid(id));
    }

    // === GET OVERHEADS BY USER ID ===
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get overhead by User ID", description = "Retrieves a specific overhead by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found with the given ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<List<OverheadResponseDTO>> getOverheadByUserId(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader)
            throws NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(overheadService.getByUserId(userId));
    }

    // === FILTERED SEARCH ===
    @GetMapping("/search")
    @Operation(summary = "Filter overheads", description = "Retrieves a list of overheads filtered by type, amount, and date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered overheads retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid token")
    })
    public ResponseEntity<List<Overhead>> getOverheadsByFilters(
            @RequestParam(required = false) String tipoDeGasto,
            @RequestParam(defaultValue = "0") double gastoMinimo,
            @RequestParam(defaultValue = "0") double gastoMaximo,
            @RequestParam(defaultValue = "0") double montoGasto,
            @RequestParam(required = false) String filtroFecha,
            @RequestHeader("Authorization") String authHeader) {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Overhead> overheads = overheadService.getByFilters(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
        return new ResponseEntity<>(overheads, HttpStatus.OK);
    }
}