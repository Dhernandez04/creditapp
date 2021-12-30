package com.creditos.app.models.dao;


import com.creditos.app.models.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ICustomerDao extends JpaRepository<Customer,Long> {

  
   
}
