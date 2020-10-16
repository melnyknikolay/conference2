package com.app03.wildcards;

import io.nats.client.*;

import java.io.IOException;

public class WCConsumerA {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("foo.*", new MessageHandler() {
                    @Override
                    public void onMessage(Message message) {
                        System.out.println("WCConsumerA2.onMessage(): " + message);

                    }
                }
        );


    }
}
