package com.loansytemapi.LoanSystem_Api.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table (name = "Overheads")
public class Overhead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer user_id;
     
    @Column(name = "overhead_type", nullable = false)
    private String overhead_type;

    @Column (name = "overhead_description", nullable = false)
    private String overhead_description;
    
    @Column(name = "ammount", nullable = false)
    private Integer ammount;
    
    @Column(name = "overhead_date", nullable = false)
    private LocalDate overhead_date = LocalDate.now();
    
    public Overhead(){}

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

    public String getOverhead_type() {
        return overhead_type;
    }

    public void setOverhead_type(String overhead_type) {
        this.overhead_type = overhead_type;
    }

    public Integer getCantidad() {
        return ammount;
    }

    public void setCantidad(Integer cantidad) {
        this.ammount = cantidad;
    }

    public LocalDate getOverhead_date() {
        return overhead_date;
    }

    public void setOverhead_date(LocalDate overhead_date) {
        this.overhead_date = overhead_date;
    }

    public String getOverhead_description() {
        return overhead_description;
    }

    public void setOverhead_description(String overhead_description) {
        this.overhead_description = overhead_description;
    }

    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }
    
    
}
