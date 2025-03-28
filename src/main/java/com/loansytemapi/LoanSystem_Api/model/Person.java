package com.loansytemapi.LoanSystem_Api.model;

import lombok.*;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private String id;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private int age;
    private String email;
    private String phone;
    private Address address;
}

