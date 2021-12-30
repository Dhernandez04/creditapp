package com.creditos.app.models.dao;

import java.util.List;

import com.creditos.app.models.entity.Customer;
import com.creditos.app.models.entity.User;

public interface  IUserDao {
    public List<User> findAll();

   
}
