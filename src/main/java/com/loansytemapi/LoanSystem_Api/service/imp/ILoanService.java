package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();
    Loan getLoanById(int id);
    Loan deleteLoan(int id);
    Loan createLoan(Loan loan);
    Loan updateLoan(int id, Loan loan);
    List<Loan> searchLoansByQuery(String query);
}
