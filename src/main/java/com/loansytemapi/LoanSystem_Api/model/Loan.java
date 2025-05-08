package com.loansytemapi.LoanSystem_Api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;

    @Column(name = "term", nullable = false)
    private double term;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    public Loan() {}

    public Loan(int id, double amount, double interestRate, double term, boolean active, LocalDate date, int userId, int clientId) {
        this.id = id;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.active = active;
        this.date = date;
        this.userId = userId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}