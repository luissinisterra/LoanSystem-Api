package com.loansytemapi.LoanSystem_Api.dto;

import com.loansytemapi.LoanSystem_Api.model.Loan;

import java.time.LocalDate;

public class LoanResponseDTO {

    private int id;
    private double amount;
    private double interestRate;
    private double term;
    private boolean active;
    private LocalDate date;
    private int clientId;
    private int userId;

    public LoanResponseDTO(int id, double amount, double interestRate, double term, boolean active, LocalDate date, int clientId, int userId) {
        this.id = id;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.active = active;
        this.date = date;
        this.clientId = clientId;
        this.userId = userId;
    }

    public LoanResponseDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getAmount();
        this.interestRate = loan.getInterestRate();
        this.term = loan.getTerm();
        this.active = loan.isActive();
        this.date = loan.getDate();
        this.clientId = loan.getClient().getId();
        this.userId = loan.getUser().getId();
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
