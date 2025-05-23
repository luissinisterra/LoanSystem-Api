package com.loansytemapi.LoanSystem_Api.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table (name = "Overheads")
public class Overhead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // nombre columna FK en la tabla
    private User user;
     
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOverhead_type() {
        return overhead_type;
    }

    public void setOverhead_type(String overhead_type) {
        this.overhead_type = overhead_type;
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
