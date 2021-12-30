package com.creditos.app.models.dao;
import java.util.List;

import com.creditos.app.models.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentDao extends JpaRepository<Payment,Long> {
    public List<Payment> findByPaymentId(Long id);

    
}
