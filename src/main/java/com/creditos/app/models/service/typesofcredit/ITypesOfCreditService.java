package com.creditos.app.models.service.typesofcredit;

import com.creditos.app.models.entity.TypeOfCredit;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITypesOfCreditService {
    public Page<TypeOfCredit> findAll(Pageable pageable);
    public void save(TypeOfCredit customer);
    public TypeOfCredit findOne(Long id);
    public void delete(Long id);
}
