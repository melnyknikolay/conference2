package com.app01;

import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

public class ProducerForAsync {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        //natsConnection.publish("topic1", "Hi there!".getBytes());
        natsConnection.publish("topic1", "Hi there2!".getBytes());
        natsConnection.close();
    }
}
