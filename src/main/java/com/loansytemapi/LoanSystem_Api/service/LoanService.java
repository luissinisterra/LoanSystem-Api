package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService implements ILoanService {

    private final ILoanRepository iLoanRepository;

    @Autowired
    public LoanService(ILoanRepository iLoanRepository) {
        this.iLoanRepository = iLoanRepository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return iLoanRepository.findAll();
    }

    @Override
    public Loan getLoanById(int id) throws NotFoundException {
        return iLoanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Préstamo no encontrado con ID: " + id));
    }

    @Override
    public Loan createLoan(Loan loan) throws IncompleteDataException {
        if (loan.getAmount() <= 0) {
            throw new IncompleteDataException("El monto debe ser mayor a cero.");
        }
        if (loan.getInterestRate() < 0) {
            throw new IncompleteDataException("La tasa de interés no puede ser negativa.");
        }
        if (loan.getTerm() <= 0) {
            throw new IncompleteDataException("El plazo debe ser mayor a cero.");
        }
        if (loan.getDate() == null) {
            throw new IncompleteDataException("La fecha es obligatoria.");
        }
        if (loan.getClient() == null) {
            throw new IncompleteDataException("El cliente es obligatorio.");
        }
        if (loan.getUser() == null) {
            throw new IncompleteDataException("El usuario es obligatorio.");
        }

        return iLoanRepository.save(loan);
    }

    @Override
    public Loan deleteLoan(int id) throws NotFoundException {
        Loan loan = getLoanById(id);
        iLoanRepository.deleteById(id);
        return loan;
    }

    @Override
    public Loan updateLoan(int id, Loan updatedLoan) throws IncompleteDataException, NotFoundException {
        Loan existingLoan = getLoanById(id);

        existingLoan.setAmount(updatedLoan.getAmount());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setTerm(updatedLoan.getTerm());
        existingLoan.setActive(updatedLoan.isActive());
        existingLoan.setDate(updatedLoan.getDate());
        existingLoan.setClient(updatedLoan.getClient());
        existingLoan.setUser(updatedLoan.getUser());

        if (existingLoan.getAmount() <= 0) {
            throw new IncompleteDataException("El monto debe ser mayor a cero.");
        }
        if (existingLoan.getInterestRate() < 0) {
            throw new IncompleteDataException("La tasa de interés no puede ser negativa.");
        }
        if (existingLoan.getTerm() <= 0) {
            throw new IncompleteDataException("El plazo debe ser mayor a cero.");
        }
        if (existingLoan.getDate() == null) {
            throw new IncompleteDataException("La fecha es obligatoria.");
        }
        if (existingLoan.getClient() == null) {
            throw new IncompleteDataException("El cliente es obligatorio.");
        }
        if (existingLoan.getUser() == null) {
            throw new IncompleteDataException("El usuario es obligatorio.");
        }

        return iLoanRepository.save(existingLoan);
    }

    @Override
    public List<Loan> searchLoansByQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return iLoanRepository.findAll();
        }
        return iLoanRepository.findLoansByCriteria(query);
    }
}