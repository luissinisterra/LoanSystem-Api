package com.loansytemapi.LoanSystem_Api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public Address() {}

    public Address(String country, String department, String city, String street, String postalCode) {
        this.country = country;
        this.department = department;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}