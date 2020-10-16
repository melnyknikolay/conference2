/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.service;

import javax.transaction.Transactional;
import com.mycompany.my.spring5.jta.org.model.Company;
import com.mycompany.my.spring5.jta.org.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denys.Prokopiuk
 */
@Service
//@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void save(Company company) {
        companyRepository.save(company);
    }
    
}
