package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}