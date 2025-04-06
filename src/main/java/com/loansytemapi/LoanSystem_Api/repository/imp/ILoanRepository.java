package com.loansytemapi.LoanSystem_Api.repository.imp;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanRepository {
    List<Loan> getAllLoans();
    Loan getLoanById(String id);
    Loan deleteLoan(String id);
    Loan createLoan(Loan loan);
    Loan updateLoan(String id, Loan loan);
    List<Loan> searchLoansByQuery(String query);
}
