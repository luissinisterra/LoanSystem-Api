package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.IncomeCreateDTO;
import com.loansytemapi.LoanSystem_Api.dto.IncomeResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidAmmountException;
import com.loansytemapi.LoanSystem_Api.exception.InvalidTextLengthException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Income;
import com.loansytemapi.LoanSystem_Api.service.imp.IIncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@Tag(name = "Incomes", description = "API for income management")
public class IncomeController {

    private final IIncomeService incomeService;

    @Autowired
    public IncomeController(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Operation(summary = "Get all incomes", description = "Returns a list of all registered incomes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income list retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getAll() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @Operation(summary = "Get income by ID", description = "Find a specific income by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income found"),
            @ApiResponse(responseCode = "404", description = "Income not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> getById(@PathVariable @Parameter(description = "ID of the income to retrieve") Integer id) throws NotFoundException {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    @Operation(
            summary = "Get incomes by user ID",
            description = "Returns a list of incomes associated with a specific user ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incomes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No incomes found for the specified user ID")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IncomeResponseDTO>> getIncomesByUserId(
            @PathVariable @Parameter(description = "User ID to retrieve incomes for") Integer userId) {
        return ResponseEntity.ok(incomeService.getByUserId(userId));
    }

    @Operation(summary = "Create a new income", description = "Saves a new income in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Income created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data for creation")
    })
    @PostMapping
    public ResponseEntity<IncomeResponseDTO> save(@RequestBody IncomeCreateDTO income) throws InvalidTextLengthException, InvalidAmmountException, NotFoundException {
        return ResponseEntity.status(201).body(incomeService.saveIncome(income));
    }

    @Operation(summary = "Update an income", description = "Modifies an existing income")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income updated successfully"),
            @ApiResponse(responseCode = "404", description = "Income not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> update(@PathVariable @Parameter(description = "ID of the income to update") Integer id, @RequestBody IncomeCreateDTO income) throws NotFoundException, InvalidTextLengthException, InvalidAmmountException {
        return ResponseEntity.ok(incomeService.updateIncome(id, income));
    }

    @Operation(summary = "Delete an income", description = "Deletes an income by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Income deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Income not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable @Parameter(description = "ID of the income to delete") Integer id) throws NotFoundException {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filter incomes", description = "Returns a list of incomes based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered income list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No incomes found with the provided filters")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Income>> getByFilters(
            @RequestParam(required = false) @Parameter(description = "Type of income to filter by (optional)") String incomeType,
            @RequestParam(required = false) @Parameter(description = "Minimum income value to filter by (optional)") Integer minimumIncome,
            @RequestParam(required = false) @Parameter(description = "Maximum income value to filter by (optional)") Integer maximumIncome,
            @RequestParam(required = false) @Parameter(description = "Exact income value to filter by (optional)") Integer incomeAmmount,
            @RequestParam(required = false) @Parameter(description = "Date filter (optional)") String dateFilter)
            throws NotFoundException {
        return ResponseEntity.ok(incomeService.getByFilter(incomeType, minimumIncome, maximumIncome, incomeAmmount, dateFilter));
    }
}
