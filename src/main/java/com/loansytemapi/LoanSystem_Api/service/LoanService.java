package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.LoanRepository;
import com.loansytemapi.LoanSystem_Api.repository.imp.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService implements ILoanService {
    private final ILoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        this.initSampleData();
    }

    // Método para inicializar datos de muestra
    public void initSampleData() {
        Loan loan1 = new Loan(
                "L001",
                null, // Aquí iría el cliente, se asignará después cuando lo usemos en Client
                15000.00,
                LocalDate.of(2025, 3, 15)
        );

        Loan loan2 = new Loan(
                "L002",
                null, // Aquí iría el cliente, se asignará después cuando lo usemos en Client
                20000.00,
                LocalDate.of(2024, 6, 1)
        );

        this.loanRepository.createLoan(loan1);
        this.loanRepository.createLoan(loan2);
    }

    @Override
    public List<Loan> getAllLoans() {
        return this.loanRepository.getAllLoans();
    }

    @Override
    public Loan getLoanById(String id) {
        return this.loanRepository.getLoanById(id);
    }

    @Override
    public Loan createLoan(Loan loan) {
        return this.loanRepository.createLoan(loan);
    }

    @Override
    public Loan deleteLoan(String id) {
        return this.loanRepository.deleteLoan(id);
    }

    @Override
    public Loan updateLoan(String id, Loan loan) {
        return this.loanRepository.updateLoan(id, loan);
    }

    @Override
    public List<Loan> searchLoansByQuery(String query) {
        return this.loanRepository.searchLoansByQuery(query);
    }
}
