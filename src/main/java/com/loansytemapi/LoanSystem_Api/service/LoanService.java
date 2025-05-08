package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.imp.ILoanRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Loan getLoanById(int id) {
        return iLoanRepository.findById(id).orElse(null);
    }

    @Override
    public Loan createLoan(Loan loan) {
        return iLoanRepository.save(loan);
    }

    @Override
    public Loan deleteLoan(int id) {
        Loan loan = getLoanById(id);
        if (loan != null) {
            iLoanRepository.deleteById(id);
        }
        return loan;
    }

    @Override
    public Loan updateLoan(int id, Loan updatedLoan) {
        updatedLoan.setId(id);
        return iLoanRepository.save(updatedLoan);
    }

    @Override
    public List<Loan> searchLoansByQuery(String query) {
        return iLoanRepository.searchLoansByQuery(query);
    }
}