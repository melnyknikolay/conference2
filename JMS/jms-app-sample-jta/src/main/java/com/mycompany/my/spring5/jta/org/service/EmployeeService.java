/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.service;

import javax.transaction.Transactional;
import com.mycompany.my.spring5.jta.org.model.Employee;
import com.mycompany.my.spring5.jta.org.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denys.Prokopiuk
 */
@Service
//@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void save(Employee emp) {
        employeeRepository.save(emp);
    }

}
