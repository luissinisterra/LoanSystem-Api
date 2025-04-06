package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.repository.imp.ILoanRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LoanRepository implements ILoanRepository {

    private final Map<String, Loan> loans = new HashMap<>();

    @Override
    public List<Loan> getAllLoans() {
        return new ArrayList<>(this.loans.values());
    }

    @Override
    public Loan getLoanById(String id) {
        return this.loans.get(id);
    }

    @Override
    public Loan createLoan(Loan loan) {
        this.loans.put(loan.getId(), loan);
        return loan;
    }

    @Override
    public Loan deleteLoan(String id) {
        Loan loan = this.loans.get(id);
        this.loans.remove(id);
        return loan;
    }

    @Override
    public Loan updateLoan(String id, Loan loan) {
        this.loans.put(id, loan);
        return loan;
    }

    @Override
    public List<Loan> searchLoansByQuery(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>(this.loans.values());
        }

        String lowerCaseQuery = query.toLowerCase();

        return this.loans.values().stream()
                .filter(loan -> {
                    return (loan.getId() != null && loan.getId().toLowerCase().contains(lowerCaseQuery)) ||
                            (loan.getClient().getFirstName() != null && loan.getClient().getFirstName().toLowerCase().contains(lowerCaseQuery)) ||
                            (loan.getClient().getFirstSurname() != null && loan.getClient().getFirstSurname().toLowerCase().contains(lowerCaseQuery)) ||
                            (loan.getAmount() == 0 && String.valueOf(loan.getAmount()).equals(query));
                }).collect(Collectors.toList());
    }
}
