package com.app.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MyJmsListener implements MessageListener {

    public void onMessage(Message message) {
        try {
            System.out.println("START: " + hashCode());
            System.out.println("\t" + message);
            System.out.println("END: " + hashCode());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
