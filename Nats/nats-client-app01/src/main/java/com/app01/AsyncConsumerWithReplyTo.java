package com.app01;

import io.nats.client.*;

import java.io.IOException;

public class AsyncConsumerWithReplyTo {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("topic1", new MessageHandler() {
            @Override
            public void onMessage(Message message) {
                System.out.println("L2" + hashCode() + ":" + message);
                if (message.getReplyTo() != null) {
                    try {

                        natsConnection.publish(message.getReplyTo(), "L1: ACK".getBytes());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }
}
