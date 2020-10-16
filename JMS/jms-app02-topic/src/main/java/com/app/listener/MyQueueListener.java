package com.app.listener;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

//@Component
public class MyQueueListener {

  //  @JmsListener(destination = "myQueue", concurrency = "10-10")
    public void onMessage(Message message){
        try {
            System.out.println("MyQueueListener.onMessage(). @START; hashCode="+hashCode()+"; thread="+Thread.currentThread().getName());
            Thread.sleep(5000);
            TextMessage msg = (TextMessage) message;
            String text = msg.getText();
            System.out.println("\tCONTENT="+text);
            System.out.println("MyQueueListener.onMessage(). @FINISH; hashCode="+hashCode()+"; thread="+Thread.currentThread().getName());
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
