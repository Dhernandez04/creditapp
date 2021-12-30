package com.creditos.app.models.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="payments")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(length = 80,unique = true)
    private Long id;

    @Column(name="installment_number")
    private Integer installmentNumber;

    private String status;

    private float value;
    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="payment_id")
    private Long paymentId;
    
    public Long getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public void setPaymentDate(Calendar paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Column(name="payment_date")
    @Temporal(TemporalType.DATE)
    private Calendar paymentDate;

    @Column(name="expiration_date")
    @Temporal(TemporalType.DATE)
    private Calendar expirationDate;
    public Payment() {
        // paymentDate =  Calendar.getInstance();
        expirationDate = Calendar.getInstance();
    }
    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    } 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(int paymentDate,int valor) {
        this.paymentDate.add(paymentDate, valor);
    }

  

    public void setExpirationDate(int paymentDate,int valor) {
        this.expirationDate.add(paymentDate, valor);
    }
    public Calendar getExpirationDate() {
        return expirationDate;
    }
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public  String formatearCalendar(Calendar c) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return df.format(c.getTime());
    }
    

}
