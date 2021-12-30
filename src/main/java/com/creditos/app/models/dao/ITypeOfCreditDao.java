package com.creditos.app.models.dao;

import com.creditos.app.models.entity.TypeOfCredit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeOfCreditDao extends JpaRepository<TypeOfCredit,Long> {
    
}
