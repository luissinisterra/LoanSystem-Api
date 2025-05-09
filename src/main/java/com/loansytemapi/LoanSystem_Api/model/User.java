package com.loansytemapi.LoanSystem_Api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "names", nullable = false)
    private String names;

    @Column(name = "surnames", nullable = false)
    private String surnames;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "gender", nullable = false)
    private String gender;

    public User() {}

    // Constructor sin ID (porque se genera automáticamente)
    public User(String names, String surnames, String email, String password, String username, String gender) {
        this.names = names;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}