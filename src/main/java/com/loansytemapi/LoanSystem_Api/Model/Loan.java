package com.loansytemapi.LoanSystem_Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Loan {
    private String id;
    private Client client;
    private double amount;
    private LocalDate date;
}
