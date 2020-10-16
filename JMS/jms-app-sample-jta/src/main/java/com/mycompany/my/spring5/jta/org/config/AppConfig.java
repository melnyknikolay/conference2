/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.config;

import com.atomikos.icatch.jta.J2eeUserTransaction;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.mycompany.my.spring5.jta.org.listener.MyListener;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import java.io.IOException;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
//import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 *
 * @author Denys.Prokopiuk
 */
@Configuration
@EnableTransactionManagement
//@EnableJms
@ComponentScan(basePackages = {"com.mycompany.my.spring5.jta.org"})
public class AppConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setUseAsyncSend(true);
        activeMQXAConnectionFactory.setAlwaysSessionAsync(true);
        activeMQXAConnectionFactory.setStatsEnabled(true);
        activeMQXAConnectionFactory.setBrokerURL("tcp://127.0.0.1:61616");

        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("NOTIFICATION-XADBMSA");
        //atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setMaxPoolSize(100);
        atomikosConnectionFactoryBean.setBorrowConnectionTimeout(30000);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestinationName("JTA-QUEUE");
        return jmsTemplate;
    }

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory() {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        dmlc.setConnectionFactory(cf);
        dmlc.setDestinationName("JTA-QUEUE");
        dmlc.setMessageListener(new MyListener());
        dmlc.setAutoStartup(true);
        return dmlc;
    }

    // DATABASE config
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource companyDataSource() {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/jta_company?useSSL=false");
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUser("root");
        mysqlXaDataSource.setPassword("11111111");

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("COMPANY-XADBMSA");
        dataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        dataSource.setXaDataSource(mysqlXaDataSource);
        dataSource.setPoolSize(1);
        return dataSource;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource employeeDataSource() {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/jta_employee?useSSL=false");
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUser("root");
        mysqlXaDataSource.setPassword("11111111");

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("EMPLOYEE-XADBMSA");
        dataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        dataSource.setXaDataSource(mysqlXaDataSource);
        dataSource.setPoolSize(1);
        return dataSource;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setForceShutdown(false);
        return manager;
    }

    @Bean
    public J2eeUserTransaction atomikosUserTransaction() {
        try {
            J2eeUserTransaction userTransaction = new J2eeUserTransaction();
            userTransaction.setTransactionTimeout(300);
            return userTransaction;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public JtaTransactionManager transactionManager() throws IOException {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(atomikosTransactionManager());
        jtaTransactionManager.setUserTransaction(atomikosUserTransaction());
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

}
