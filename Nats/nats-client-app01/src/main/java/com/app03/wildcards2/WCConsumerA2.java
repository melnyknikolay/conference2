package com.app03.wildcards2;

import io.nats.client.*;

import java.io.IOException;

public class WCConsumerA2 {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("foo.>", new MessageHandler() {
                    @Override
                    public void onMessage(Message message) {
                        System.out.println("WCConsumerA2.onMessage(): " + message);

                    }
                }
        );


    }
}
