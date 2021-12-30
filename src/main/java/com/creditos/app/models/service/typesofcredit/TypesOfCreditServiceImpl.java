package com.creditos.app.models.service.typesofcredit;

import java.util.List;

import com.creditos.app.models.dao.ITypeOfCreditDao;
import com.creditos.app.models.entity.TypeOfCredit;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TypesOfCreditServiceImpl implements ITypesOfCreditService{
    @Autowired ITypeOfCreditDao typeOfCreditDao;

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfCredit> findAll(Pageable pageable) {
        return typeOfCreditDao.findAll(pageable);
    }
    public List<TypeOfCredit> findAll() {
        return typeOfCreditDao.findAll();
    }
    @Override
    @Transactional
    public void save(TypeOfCredit payment) {
        typeOfCreditDao.save(payment);
        
    }

    @Override
    @Transactional(readOnly = true)
    public TypeOfCredit findOne(Long id) {
        return typeOfCreditDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        typeOfCreditDao.deleteById(id);
    }
}
