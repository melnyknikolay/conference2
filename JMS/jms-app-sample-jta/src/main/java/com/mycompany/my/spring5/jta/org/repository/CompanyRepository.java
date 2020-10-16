/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import com.mycompany.my.spring5.jta.org.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denys.Prokopiuk
 */
@Repository
@Transactional
public class CompanyRepository {

    @Autowired
    @Qualifier("companyDataSource")
    private DataSource dataSource;

    //  @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void save(Company company) {
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO company(company_name) VALUES(?)");
            ps.setObject(1, company.getCompanyName());
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
