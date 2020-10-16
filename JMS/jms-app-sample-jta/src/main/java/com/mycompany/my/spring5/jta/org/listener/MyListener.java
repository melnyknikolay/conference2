/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.my.spring5.jta.org.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Denys.Prokopiuk
 */
public class MyListener implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        System.out.println("!!!!!MyListener.onMessage()");
    }

}
