package com.creditos.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="customers")
public class Customer implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(length = 80,unique = true)
    private Long dni;
    
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String address;
    @NotEmpty
    private String telephone;

    private Boolean active;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<Credit> credits;
    
    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    public Customer() {
        credits = new ArrayList<Credit>();
    }
    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }
    public Long getDni() {
        return dni;
    }
    public void setDni(Long dni) {
        this.dni = dni;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public List<Credit> getCredits() {
        return credits;
    }
    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public void addCredit(Credit credit){
        this.credits.add(credit);
    }

}
