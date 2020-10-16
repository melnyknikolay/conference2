package com.app05.queue.group;

import io.nats.client.*;

import java.io.IOException;

public class QConsumerG1B {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("myTopic", "G1", new MessageHandler() {

                    @Override
                    public void onMessage(Message message) {
                        System.out.println("QConsumerG1B.onMessage(): " + message);
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
