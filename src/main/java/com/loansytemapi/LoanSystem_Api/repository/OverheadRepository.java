package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.Overhead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverheadRepository extends JpaRepository<Overhead, Integer> {
}
