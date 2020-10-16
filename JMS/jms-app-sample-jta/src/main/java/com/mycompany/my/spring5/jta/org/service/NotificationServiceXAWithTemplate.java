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
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denys.Prokopiuk
 */
@Service
public class NotificationServiceXAWithTemplate {

    @Autowired
    private JmsTemplate jmsTemplate;

    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    @Transactional(Transactional.TxType.REQUIRED)
    public void makeNotification() {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session sn) throws JMSException {
                return sn.createTextMessage("Template Test");
            }
        });

    }

}
