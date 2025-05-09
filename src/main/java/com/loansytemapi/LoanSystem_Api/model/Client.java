package com.loansytemapi.LoanSystem_Api.model;

import com.loansytemapi.LoanSystem_Api.controller.LoanController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends Person {

    @Column(name = "active", nullable = false)
    private boolean active;

    private List<Loan> loans;

    public Client() {}

    public Client(int id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, int addressId) {
        super(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, addressId);
        this.active = true;
        this.loans = new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
