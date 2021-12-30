package com.creditos.app.models.service.customer;



import java.util.List;

import com.creditos.app.models.dao.ICustomerDao;
import com.creditos.app.models.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements ICustomerService{
 
    @Autowired ICustomerDao customerDao;
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        return customerDao.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerDao.findAll();
    }
    @Override
    @Transactional
    public void save(Customer customer) {
        customerDao.save(customer);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerDao.deleteById(id);
    }
    
}
