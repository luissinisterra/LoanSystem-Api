package com.loansytemapi.LoanSystem_Api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class Client extends Person {
    private Map<String, Loan> loans;

    public Client(String id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, Address address) {
        super(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address);
        this.loans = new HashMap<>();
    }
}
