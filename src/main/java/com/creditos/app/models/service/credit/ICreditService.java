package com.creditos.app.models.service.credit;

import com.creditos.app.models.entity.Credit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICreditService {
    public Page<Credit> findAll(Pageable pageable);
    public void save(Credit customer);
    public Credit findOne(Long id);
    public void delete(Long id);
}
