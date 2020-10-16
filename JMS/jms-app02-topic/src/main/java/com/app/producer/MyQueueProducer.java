package com.app.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class MyQueueProducer {

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Destination destinationTopic;

    public void send(String text) {
        try {
            Connection c = connectionFactory.createConnection();
            Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Message message = session.createTextMessage(text);
            MessageProducer producer = session.createProducer(destinationTopic);
            producer.send(message);
            System.out.println("Data was pubished="+text);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
