package com.app.listener;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Enumeration;

@Component
public class MyQueueListenerC {

    @JmsListener(destination = "myTopic2", containerFactory = "myTopicFactory")
    public void onMessage(Message message){
        try {
            System.out.println("MyQueueListenerC.onMessage(). @START");
            TextMessage msg = (TextMessage) message;
            String text = msg.getText();
            System.out.println(text);
            System.out.println("-----header------------start");
            Enumeration enumeration = message.getPropertyNames();
            while(enumeration.hasMoreElements()){
                String headerName = enumeration.nextElement()+"";
                System.out.println(headerName+"="+msg.getStringProperty(headerName));

            }
            System.out.println("-----header------------end");
            System.out.println("MyQueueListenerC.onMessage(). @FINISH");
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
