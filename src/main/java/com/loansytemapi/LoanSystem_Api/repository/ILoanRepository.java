package com.loansytemapi.LoanSystem_Api.repository.imp;

import com.loansytemapi.LoanSystem_Api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ILoanRepository extends JpaRepository<Loan, Integer> {

    @Query("SELECT l FROM Loan l WHERE " +
            "LOWER(l.id) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(l.client.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(l.client.firstSurname) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Loan> searchLoansByQuery(@Param("query") String query);
}
