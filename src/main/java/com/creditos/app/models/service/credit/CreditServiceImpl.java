package com.creditos.app.models.service.credit;

import java.util.Calendar;
import java.util.List;

import com.creditos.app.models.dao.ICreditDao;
import com.creditos.app.models.dao.IPaymentDao;
import com.creditos.app.models.entity.Credit;
import com.creditos.app.models.entity.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditServiceImpl implements ICreditService{
    @Autowired ICreditDao creditDao;
    @Autowired IPaymentDao paymentDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Credit> findAll(Pageable pageable) {
        return creditDao.findAll(pageable);
    }
    public List<Credit> findAll() {
        return creditDao.findAll();
    }
    @Override
    @Transactional
    public void save(Credit credit) {
        if(credit.getId()!= null){
            creditDao.save(credit);  
        }else{
            if(credit.getTypeOfCredit().getId() == 1){
           
                for(int i=1; i<=credit.getNumberOfInstallments();i++){
                    Payment payment = new Payment();
                    payment.setInstallmentNumber(i);
                    payment.setValue(credit.getFeeAmount());
                    payment.setExpirationDate(Calendar.MONTH,i);
                    payment.setStatus("PENDIENTE");
                    credit.addPayment(payment);
                    
                    
                    payment =null;
                }
                creditDao.save(credit);
            }else if(credit.getTypeOfCredit().getId()==2){
                int days=15;
                for(int i=1; i<=credit.getNumberOfInstallments();i++){
                   
                        Payment payment = new Payment();
                        payment.setInstallmentNumber(i);
                        payment.setValue(credit.getFeeAmount());
                        payment.setExpirationDate(Calendar.DAY_OF_YEAR,days);
                        payment.setStatus("PENDIENTE");
                        credit.addPayment(payment);
                        days+=15;
                        payment =null;
                    
                }
                creditDao.save(credit);
            }else if(credit.getTypeOfCredit().getId() ==3){
                int days=7;
                for(int i=1; i<=credit.getNumberOfInstallments();i++){
                   
                        Payment payment = new Payment();
                        payment.setInstallmentNumber(i);
                        payment.setValue(credit.getFeeAmount());
                        payment.setExpirationDate(Calendar.DAY_OF_YEAR,days);
                        payment.setStatus("PENDIENTE");
                        credit.addPayment(payment);
                        days+=7;
                        payment =null;
                }
                creditDao.save(credit);   
        }
       
       
    }
}

    @Override
    @Transactional(readOnly = true)
    public Credit findOne(Long id) {
        return creditDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        creditDao.deleteById(id);
    }
}
