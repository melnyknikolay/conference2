/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.service;

import javax.transaction.Transactional;
import com.mycompany.my.spring5.jta.org.model.Company;
import com.mycompany.my.spring5.jta.org.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denys.Prokopiuk
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class BusinessService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private NotificationServiceXA notificationService;
    @Autowired
    private NotificationServiceXAWithTemplate notificationServiceXAWithTemplate;


    //TR1
    @Transactional(value = Transactional.TxType.REQUIRED, dontRollbackOn = {NullPointerException.class})
    public void process(Employee employee, Company company) {
        //TR1
        notificationService.makeNotification();
        //TR1
        notificationServiceXAWithTemplate.makeNotification();
        //TR2
        companyService.save(company);
        //TR1
        employeeService.save(employee);
        String s = null;
        s.toString();
    }
}
