package com.app04.request.reply2;

import io.nats.client.*;

import java.io.IOException;

public class MyRRConsumer2 {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("ttt", new MessageHandler() {
                    @Override
                    public void onMessage(Message message) {
                        System.out.println("WCConsumerB2.onMessage(): " + message);
                        try {
                            natsConnection.publish(message.getReplyTo(), "RESPONSE: OK".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }

}
