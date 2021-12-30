package com.creditos.app.models.dao;

import com.creditos.app.models.entity.Credit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICreditDao extends JpaRepository<Credit,Long> {
    
}
