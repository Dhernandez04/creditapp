package com.creditos.app.models.service.customer;




import com.creditos.app.models.entity.Customer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    public Page<Customer> findAll(Pageable pageable);
    public void save(Customer customer);
    public Customer findOne(Long id);
    public void delete(Long id);
}
