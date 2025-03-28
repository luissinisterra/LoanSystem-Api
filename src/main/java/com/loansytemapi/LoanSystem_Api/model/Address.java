package com.loansytemapi.LoanSystem_Api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
    private String country;
    private String deparment;
    private String city;
    private String street;
    private String postalCode;
}


