package com.app02.resilience;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;

import java.io.IOException;

public class SimpleProducer {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = initConnection();
        Thread.sleep(5000);
        System.out.println("@After sleep");
        natsConnection.publish("simpleTopic", "dataMessage".getBytes());
        System.out.println("@After publish");
        Thread.sleep(10000);
        natsConnection.publish("simpleTopic", "dataMessage2".getBytes());
        System.out.println("@After publish #2");
        //natsConnection.close();
    }

    private static Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .errorCb(e->{e.printStackTrace();})

                .disconnectedCb(event -> System.out.println("Channel disconnected: " + event.getConnection()))
                .reconnectedCb(event -> System.out.println("Reconnected to server: " + event.getConnection()))
                .build();

        return Nats.connect("nats://localhost:4222", options);
    }

}
