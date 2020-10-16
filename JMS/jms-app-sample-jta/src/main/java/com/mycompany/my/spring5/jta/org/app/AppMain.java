/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.app;

import com.mycompany.my.spring5.jta.org.config.AppConfig;
import com.mycompany.my.spring5.jta.org.model.Company;
import com.mycompany.my.spring5.jta.org.model.Employee;
import com.mycompany.my.spring5.jta.org.service.BusinessService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Denys.Prokopiuk
 */
public class AppMain {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BusinessService businessService = ctx.getBean(BusinessService.class);
        
        Employee emp = new Employee();
        emp.setAge(10);
        emp.setName("Emp1");
        
        Company company = new Company();        
        company.setCompanyName("CompanyA");
        
        businessService.process(emp, company);
                  
    }
    
}
