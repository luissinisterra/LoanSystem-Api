package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.service.LoanService;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final ILoanService iLoanService;

    @Autowired
    public LoanController(ILoanService iLoanService) {
        this.iLoanService = iLoanService;
    }

    // Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = this.iLoanService.getAllLoans();
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    // Obtener un préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String id) {
        Loan loan = this.iLoanService.getLoanById(id);
        if (loan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan newLoan) {
        if (newLoan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Loan loan = this.iLoanService.createLoan(newLoan);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    // Actualizar un préstamo existente
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String id, @RequestBody Loan updatedLoan) {
        if (updatedLoan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Loan loan = this.iLoanService.updateLoan(id, updatedLoan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // Eliminar un préstamo
    @DeleteMapping("/{id}")
    public ResponseEntity<Loan> deleteLoan(@PathVariable String id) {
        Loan loan = this.iLoanService.deleteLoan(id);
        if (loan == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // Buscar préstamos por consulta
    @GetMapping("/search")
    public ResponseEntity<List<Loan>> searchLoans(@RequestParam(required = false) String query) {
        List<Loan> loans = this.iLoanService.searchLoansByQuery(query);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}
