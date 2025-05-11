package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();
    Loan getLoanById(int id) throws NotFoundException;
    Loan deleteLoan(int id) throws NotFoundException;
    Loan createLoan(Loan loan) throws IncompleteDataException;
    Loan updateLoan(int id, Loan loan) throws IncompleteDataException, NotFoundException;
    List<Loan> searchLoansByQuery(String query);
}
