package com.app04.request.reply;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class MyRRProducer {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        natsConnection.subscribe("tttback", message -> {
            System.out.println("Reply messages: "+message);
        });
        natsConnection.publish("ttt","tttback", "dataMessage".getBytes());


    }
}
