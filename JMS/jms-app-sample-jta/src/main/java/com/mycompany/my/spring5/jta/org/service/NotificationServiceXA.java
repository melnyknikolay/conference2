/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denys.Prokopiuk
 */
@Transactional()
@Service
public class NotificationServiceXA {

    @Autowired
    private ConnectionFactory connectionFactory;

    public void makeNotification() {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            TextMessage textMessage = session.createTextMessage("test");
            Destination destination = session.createQueue("JTA-QUEUE");
            MessageProducer producer = session.createProducer(destination);
            producer.send(textMessage);
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }
}
