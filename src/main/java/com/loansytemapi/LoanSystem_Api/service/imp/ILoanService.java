package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();
    Loan getLoanById(String id);
    Loan deleteLoan(String id);
    Loan createLoan(Loan loan);
    Loan updateLoan(String id, Loan loan);
    List<Loan> searchLoansByQuery(String query);
}
