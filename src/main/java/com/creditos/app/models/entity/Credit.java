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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="credits")
public class Credit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(length = 80,unique = true)
    private Long id;

    @Column(name="interent_rate")
    private float interestRate;

    @Column(name="credit_value")
    private float creditValue;

    @Column(name="number_of_installments")
    private Integer numberOfInstallments;

    @Column(name="interest_generate")
    private float interestGenerated;

    @Column(name="fee_amount")
    private float feeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name ="payment_id")
    private List<Payment> payment;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name ="type_of_credit_id")
    private TypeOfCredit typeOfCredit;


    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    
    public Credit() {
        this.payment = new ArrayList<Payment>();
    }

    //Events
    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public float getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(float creditValue) {
        this.creditValue = creditValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

   

    public List<Payment> getPayment() {
        return payment;
    }
    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }
    public void addPayment(Payment payment) {
        this.payment.add(payment);
    }
    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public float getInterestGenerated() {
        return interestGenerated;
    }

    public void setInterestGenerated(float interestGenerated) {
        this.interestGenerated = interestGenerated;
    }

    public float getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(float feeAmount) {
        this.feeAmount = feeAmount;
    }

    public TypeOfCredit getTypeOfCredit() {
        return typeOfCredit;
    }

    public void setTypeOfCredit(TypeOfCredit typeOfCredit) {
        this.typeOfCredit = typeOfCredit;
    }


}
