package com.app04.request.reply2;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;

public class MyRRProducer2 {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();

        //natsConnection.publish("ttt","tttback", "dataMessage".getBytes());

        Message reply = natsConnection.request("ttt", "dataMessage".getBytes(), 100);
        System.out.println("Reply: " +reply);
    }
}
