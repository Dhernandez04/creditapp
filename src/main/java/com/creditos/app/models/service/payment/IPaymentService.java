package com.creditos.app.models.service.payment;

import java.util.List;

import com.creditos.app.models.entity.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentService {
    public Page<Payment> findAll(Pageable pageable);
    public void save(Payment customer);
    public List<Payment> findByCredit(Long id);
    public Payment findOne(Long id);
    public void delete(Long id);
}
