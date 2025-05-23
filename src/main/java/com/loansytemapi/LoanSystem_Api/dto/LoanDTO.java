package com.loansytemapi.LoanSystem_Api.dto;

import java.time.LocalDate;

public class LoanDTO {

    private double amount;
    private double interestRate;
    private int term;
    private boolean active;
    private LocalDate date;
    private int clientId;
    private int userId;

    public LoanDTO(double amount, double interestRate, int term, boolean active, LocalDate date, int clientId, int userId) {
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.active = active;
        this.date = date;
        this.clientId = clientId;
        this.userId = userId;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
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

