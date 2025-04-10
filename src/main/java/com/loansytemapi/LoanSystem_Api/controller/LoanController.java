package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
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
@RequestMapping("/api/loans")
@Tag(name = "Préstamos", description = "API para la gestión de préstamos")
public class LoanController {

    private final ILoanService iLoanService;

    @Autowired
    public LoanController(ILoanService iLoanService) {
        this.iLoanService = iLoanService;
    }

    @Operation(summary = "Obtener todos los préstamos", description = "Retorna una lista con todos los préstamos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = this.iLoanService.getAllLoans();
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Operation(summary = "Obtener préstamo por ID", description = "Retorna un préstamo específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado exitosamente"),
            @ApiResponse(responseCode = "204", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(
            @PathVariable @Parameter(description = "ID del préstamo a buscar") String id) {
        Loan loan = this.iLoanService.getLoanById(id);
        if (loan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo préstamo", description = "Crea y guarda un nuevo préstamo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado exitosamente"),
            @ApiResponse(responseCode = "204", description = "Datos del préstamo incompletos")
    })
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan newLoan) {
        if (newLoan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Loan loan = this.iLoanService.createLoan(newLoan);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar préstamo", description = "Actualiza los datos de un préstamo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado exitosamente"),
            @ApiResponse(responseCode = "204", description = "Datos del préstamo incompletos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(
            @PathVariable @Parameter(description = "ID del préstamo a actualizar") String id,
            @RequestBody Loan updatedLoan) {
        if (updatedLoan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Loan loan = this.iLoanService.updateLoan(id, updatedLoan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar préstamo", description = "Elimina un préstamo del sistema mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo eliminado exitosamente"),
            @ApiResponse(responseCode = "204", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Loan> deleteLoan(
            @PathVariable @Parameter(description = "ID del préstamo a eliminar") String id) {
        Loan loan = this.iLoanService.deleteLoan(id);
        if (loan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @Operation(summary = "Buscar préstamos por consulta", description = "Filtra préstamos según el texto ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados con la búsqueda"),
            @ApiResponse(responseCode = "204", description = "No se encontraron préstamos con ese criterio")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Loan>> searchLoans(
            @RequestParam(required = false) @Parameter(description = "Texto de búsqueda para filtrar préstamos") String query) {
        List<Loan> loans = this.iLoanService.searchLoansByQuery(query);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}
