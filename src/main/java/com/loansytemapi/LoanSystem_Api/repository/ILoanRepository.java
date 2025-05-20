package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import com.loansytemapi.LoanSystem_Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ILoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findAllByClient_Id(int clientId);
    List<Loan> findAllByUser_Id(int userId);
}
