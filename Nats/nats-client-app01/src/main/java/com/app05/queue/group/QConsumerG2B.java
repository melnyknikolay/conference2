package com.app05.queue.group;

import io.nats.client.*;

import java.io.IOException;

public class QConsumerG2B {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("myTopic", "G2", new MessageHandler() {

                    @Override
                    public void onMessage(Message message) {
                        System.out.println("QConsumerG2B.onMessage(): " + message);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }
}
