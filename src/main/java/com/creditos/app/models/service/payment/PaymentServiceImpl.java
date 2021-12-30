package com.creditos.app.models.service.payment;
import java.util.List;

import com.creditos.app.models.dao.IPaymentDao;
import com.creditos.app.models.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PaymentServiceImpl implements IPaymentService{
    @Autowired IPaymentDao paymentDao;
    @Override
    @Transactional(readOnly = true)
    public Page<Payment> findAll(Pageable pageable) {
        return paymentDao.findAll(pageable);
    }
    public List<Payment> findAll() {
        return paymentDao.findAll();
    }
    @Override
    @Transactional
    public void save(Payment payment) {
        paymentDao.save(payment);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Payment findOne(Long id) {
        return paymentDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        paymentDao.deleteById(id);
    }
    @Override
    public List<Payment> findByCredit(Long id) {
       
        return paymentDao.findByPaymentId(id);
    }
}
