package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
import com.loansytemapi.LoanSystem_Api.dto.LoanResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
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
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = this.iLoanService.getAllLoans();
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    @Operation(summary = "Obtener todos los préstamos de un cliente en específico", description = "Retorna una lista con todos los préstamos registrados que tiene un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles")
    })
    @GetMapping("/client-loans/{userId}") // Aquí userId en la URL
    public ResponseEntity<List<LoanResponseDTO>> getAllLoansByClientId(@PathVariable int userId) throws NotFoundException {
        List<LoanResponseDTO> clientLoans = this.iLoanService.getAllLoansByClientId(userId);
        if (clientLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientLoans, HttpStatus.OK);
    }


    @Operation(summary = "Obtener todos los préstamos de un usuario en especifico", description = "Retorna una lista con todos los préstamos registrados que tiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay préstamos disponibles")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoansByUserId(@PathVariable int userId) throws NotFoundException {
        List<LoanResponseDTO> userLoans = this.iLoanService.getAllLoansByUserId(userId);
        if (userLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userLoans, HttpStatus.OK);
    }

    @Operation(summary = "Obtener préstamo por ID", description = "Retorna un préstamo específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoanById(
            @PathVariable @Parameter(description = "ID del préstamo a buscar") int id) {
        try {
            LoanResponseDTO loan = this.iLoanService.getLoanById(id);
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un nuevo préstamo", description = "Crea y guarda un nuevo préstamo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos")
    })
    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody LoanDTO newLoan) throws IncompleteDataException, NotFoundException {
        if (newLoan == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LoanResponseDTO loan = this.iLoanService.createLoan(newLoan);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar préstamo", description = "Actualiza los datos de un préstamo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos incompletos o inválidos"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> updateLoan(
            @PathVariable @Parameter(description = "ID del préstamo a actualizar") int id,
            @RequestBody @Parameter(description = "Datos actualizados del préstamo") LoanDTO updatedLoan)
            throws IncompleteDataException, NotFoundException {

        if (updatedLoan == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LoanResponseDTO loan = this.iLoanService.updateLoan(id, updatedLoan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar préstamo", description = "Elimina un préstamo del sistema mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> deleteLoan(
            @PathVariable @Parameter(description = "ID del préstamo a eliminar") int id) {
        try {
            LoanResponseDTO loan = this.iLoanService.deleteLoan(id);
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Buscar préstamos por consulta", description = "Filtra préstamos según el texto ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados con la búsqueda"),
            @ApiResponse(responseCode = "204", description = "No se encontraron préstamos con ese criterio")
    })
    @GetMapping("/search/{userId}")
    public ResponseEntity<List<LoanResponseDTO>> searchLoans(@PathVariable int userId,
            @RequestParam(required = false) @Parameter(description = "Texto de búsqueda para filtrar préstamos") String query) {
        List<LoanResponseDTO> loans = this.iLoanService.searchLoansByQuery(userId, query);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}