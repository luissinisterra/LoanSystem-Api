package com.loansytemapi.LoanSystem_Api.dto;

import com.loansytemapi.LoanSystem_Api.model.User;

public class UserResponseDTO {

    private int id;
    private String names;
    private String surnames;
    private String email;
    private String password;
    private String username;
    private String gender;

    public UserResponseDTO(int id, String names, String surnames, String email, String password, String username, String gender) {
        this.id = id;
        this.names = names;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
    }

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.names = user.getNames();
        this.surnames = user.getSurnames();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.gender = user.getGender();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
