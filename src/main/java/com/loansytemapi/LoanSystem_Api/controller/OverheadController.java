package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Overhead;
import com.loansytemapi.LoanSystem_Api.service.imp.IOverheadService;
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

    @Autowired
    public OverheadController(IOverheadService overheadService) {
        this.overheadService = overheadService;
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Create a new overhead", description = "Creates a new overhead (expense) for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Overhead created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid overhead data")
    })
    public ResponseEntity<Overhead> createOverhead(
            @RequestBody @Parameter(description = "Overhead data to be created") Overhead overhead)
            throws IncompleteDataException, InvalidTextLengthException, InvalidAmmountException {
        Overhead created = overheadService.save(overhead);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an overhead", description = "Deletes a user's overhead by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Overhead deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found")
    })
    public ResponseEntity<Void> deleteOverhead(
            @PathVariable @Parameter(description = "ID of the overhead to delete") Integer id)
            throws NotFoundException {
        overheadService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update an overhead", description = "Updates the details of an existing overhead.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead updated successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found")
    })
    public ResponseEntity<Overhead> updateOverhead(
            @PathVariable @Parameter(description = "ID of the overhead to update") Integer id,
            @RequestBody @Parameter(description = "Updated overhead details") Overhead overhead)
            throws NotFoundException {
        overhead.setId(id);
        Overhead updated = overheadService.update(overhead);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // LIST ALL
    @GetMapping
    @Operation(summary = "Get all overheads", description = "Retrieves all overheads registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overheads retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Overhead>> getAllOverheads() {
        return new ResponseEntity<>(overheadService.getAll(), HttpStatus.OK);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get overhead by ID", description = "Retrieves an overhead by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead found"),
            @ApiResponse(responseCode = "404", description = "Overhead not found")
    })
    public ResponseEntity<Overhead> getOverheadById(
            @PathVariable @Parameter(description = "ID of the overhead to retrieve") Integer id)
            throws NotFoundException {
        Overhead found = overheadService.getByid(id);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @Operation(
            summary = "Get overhead by ID",
            description = "Retrieves a specific overhead by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overhead retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Overhead not found with the given ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Overhead> getOverheadByUserId(
            @PathVariable @Parameter(description = "ID of the overhead to retrieve") Integer id)
            throws NotFoundException {
        Overhead found = overheadService.getByid(id);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    // FILTERED SEARCH
    @GetMapping("/search")
    @Operation(summary = "Filter overheads", description = "Retrieves a list of overheads filtered by type, amount, and date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered overheads retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Overhead>> getOverheadsByFilters(
            @RequestParam(required = false) @Parameter(description = "Overhead type to filter by") String tipoDeGasto,
            @RequestParam(defaultValue = "0") @Parameter(description = "Minimum overhead amount") double gastoMinimo,
            @RequestParam(defaultValue = "0") @Parameter(description = "Maximum overhead amount") double gastoMaximo,
            @RequestParam(defaultValue = "0") @Parameter(description = "Exact overhead amount") double montoGasto,
            @RequestParam(required = false) @Parameter(description = "Date filter (e.g., '1 week', '1 month')") String filtroFecha) {

        List<Overhead> overheads = overheadService.getByFilters(tipoDeGasto, gastoMinimo, gastoMaximo, montoGasto, filtroFecha);
        return new ResponseEntity<>(overheads, HttpStatus.OK);
    }
}
