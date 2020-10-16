package com.app05.queue.group;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class QProducer {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        natsConnection.publish("myTopic", "dataMessage".getBytes());
        natsConnection.publish("myTopic", "dataMessage2".getBytes());
    }

}
