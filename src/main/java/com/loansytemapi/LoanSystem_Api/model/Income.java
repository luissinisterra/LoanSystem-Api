package com.loansytemapi.LoanSystem_Api.model;

import java.time.LocalDate;
import jakarta.persistence.*;
@Entity
@Table (name = "Incomes")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
     @Column(name = "user_id", nullable = false)
    private Integer user_id;
     
    @Column(name = "income_type", nullable = false)
    private String income_type;

    @Column (name = "income_description", nullable = false)
    private String income_description;
    
    @Column(name = "ammount", nullable = false)
    private Integer ammount;
    
    @Column(name = "income_date", nullable = false)
    private LocalDate income_date = LocalDate.now();
    
    public Income(){
    }

    
    //Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getIncome_type() {
        return income_type;
    }

    public void setIncome_type(String income_type) {
        this.income_type = income_type;
    }

    public String getIncome_description() {
        return income_description;
    }

    public void setIncome_description(String income_description) {
        this.income_description = income_description;
    }

    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }

    public LocalDate getIncome_date() {
        return income_date;
    }

    public void setIncome_date(LocalDate income_date) {
        this.income_date = income_date;
    }
    
    
    
}
