package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.LoanDTO;
import com.loansytemapi.LoanSystem_Api.dto.LoanResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.IncompleteDataException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.Loan;
import java.util.List;

public interface ILoanService {
    List<LoanResponseDTO> getAllLoans();
    List<LoanResponseDTO> getAllLoansByClientId(int clientId) throws NotFoundException;
    List<LoanResponseDTO> getAllLoansByUserId(int userId) throws NotFoundException;
    LoanResponseDTO getLoanById(int id) throws NotFoundException;
    LoanResponseDTO deleteLoan(int id) throws NotFoundException;
    LoanResponseDTO createLoan(LoanDTO loan) throws IncompleteDataException, NotFoundException;
    LoanResponseDTO updateLoan(int id, LoanDTO loan) throws IncompleteDataException, NotFoundException;
    List<LoanResponseDTO> searchLoansByQuery(int userId, String query);

    List<LoanResponseDTO> searchByDates(String range, int userId);
}
