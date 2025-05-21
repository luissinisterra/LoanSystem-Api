package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
import com.loansytemapi.LoanSystem_Api.dto.LoanResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();
    List<Loan> getAllLoansByClientId(int clientId) throws NotFoundException;
    List<Loan> getAllLoansByUserId(int userId) throws NotFoundException;
    LoanResponseDTO getLoanById(int id) throws NotFoundException;
    LoanResponseDTO deleteLoan(int id) throws NotFoundException;
    Loan createLoan(LoanDTO loan) throws IncompleteDataException, NotFoundException;
    Loan updateLoan(int id, LoanDTO loan) throws IncompleteDataException, NotFoundException;
    List<Loan> searchLoansByQuery(int userId, String query);
}
