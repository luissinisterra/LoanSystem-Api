package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
import com.loansytemapi.LoanSystem_Api.dto.LoanResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import com.loansytemapi.LoanSystem_Api.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final JwtService jwtService;

    @Autowired
    public LoanController(ILoanService iLoanService, JwtService jwtService) {
        this.iLoanService = iLoanService;
        this.jwtService = jwtService;
    }

    // === GET ALL LOANS ===
    @GetMapping
    @Operation(summary = "Obtener todos los préstamos", description = "Retorna una lista con todos los préstamos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans(@RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<LoanResponseDTO> loans = this.iLoanService.getAllLoans();
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    // === GET LOANS BY CLIENT ID ===
    @GetMapping("/client-loans/{userId}")
    @Operation(summary = "Obtener todos los préstamos de un cliente en específico", description = "Retorna una lista con todos los préstamos registrados que tiene un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<LoanResponseDTO>> getAllLoansByClientId(
            @PathVariable int userId,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<LoanResponseDTO> clientLoans = this.iLoanService.getAllLoansByClientId(userId);
        if (clientLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientLoans, HttpStatus.OK);
    }

    // === GET LOANS BY USER ID ===
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener todos los préstamos de un usuario en específico", description = "Retorna una lista con todos los préstamos registrados que tiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<LoanResponseDTO>> getAllLoansByUserId(
            @PathVariable int userId,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<LoanResponseDTO> userLoans = this.iLoanService.getAllLoansByUserId(userId);
        if (userLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userLoans, HttpStatus.OK);
    }

    // === GET LOAN BY ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Obtener préstamo por ID", description = "Retorna un préstamo específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<LoanResponseDTO> getLoanById(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoanResponseDTO loan = this.iLoanService.getLoanById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // === CREATE LOAN ===
    @PostMapping
    @Operation(summary = "Crear un nuevo préstamo", description = "Crea y guarda un nuevo préstamo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<LoanResponseDTO> createLoan(
            @RequestBody LoanDTO newLoan,
            @RequestHeader("Authorization") String authHeader) throws IncompleteDataException, NotFoundException {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (newLoan == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LoanResponseDTO loan = this.iLoanService.createLoan(newLoan);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    // === UPDATE LOAN ===
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar préstamo", description = "Actualiza los datos de un préstamo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<LoanResponseDTO> updateLoan(
            @PathVariable int id,
            @RequestBody LoanDTO updatedLoan,
            @RequestHeader("Authorization") String authHeader)
            throws IncompleteDataException, NotFoundException {

        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (updatedLoan == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LoanResponseDTO loan = this.iLoanService.updateLoan(id, updatedLoan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // === DELETE LOAN ===
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar préstamo", description = "Elimina un préstamo del sistema mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<LoanResponseDTO> deleteLoan(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader) throws NotFoundException {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoanResponseDTO loan = this.iLoanService.deleteLoan(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // === SEARCH LOANS ===
    @GetMapping("/search/{userId}")
    @Operation(summary = "Buscar préstamos por consulta", description = "Filtra préstamos según el texto ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados con la búsqueda"),
            @ApiResponse(responseCode = "204", description = "No se encontraron préstamos con ese criterio"),
            @ApiResponse(responseCode = "401", description = "No autorizado: token inválido o ausente")
    })
    public ResponseEntity<List<LoanResponseDTO>> searchLoans(
            @PathVariable int userId,
            @RequestParam(required = false) String query,
            @RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token == null || !jwtService.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<LoanResponseDTO> loans = this.iLoanService.searchLoansByQuery(userId, query);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    public ResponseEntity<List<LoanResponseDTO>> searchLoansByDateRanges(@PathVariable int userId, @RequestParam(required = false) String Date) {
        List<LoanResponseDTO> loans = this.iLoanService.searchByDates(Date, userId);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}