package com.app03.wildcards2;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class WildCardProducer2 {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        natsConnection.publish("foo.topic1", "dataMessage".getBytes());
        natsConnection.publish("foo.topic2", "dataMessage2".getBytes());
        natsConnection.publish("foo.topic1.subdomain", "dataMessage3".getBytes());
        natsConnection.publish("foo.topic2.subdomain", "dataMessage4".getBytes());
    }

}
