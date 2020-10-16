package com.app01;

import io.nats.client.*;

import javax.sql.rowset.Predicate;
import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    private final Logger log = Logger.getLogger("app");

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Connection natsConnection = Nats.connect();
        //Connection natsConnection = main.initConnection();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("topic1", new MessageHandler() {
            @Override
            public void onMessage(Message message) {
                System.out.println("------------------");
                System.out.println(message.getClass());
                System.out.println(message);
                System.out.println(new String(message.getData()));
                try {
                    natsConnection.publish(message.getReplyTo(), "MESSAGE STATUS: OK".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------");
            }
        });
        //asyncSubscription.unsubscribe();
        SyncSubscription syncSubscription = natsConnection.subscribeSync("topic2");
        Message msg = syncSubscription.nextMessage();
        byte[] data = msg.getData();
        //syncSubscription.unsubscribe();
    }

    private Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .errorCb(e->{})
                .disconnectedCb(event -> System.out.println("Channel disconnected: " + event.getConnection()))
                .reconnectedCb(event -> System.out.println("Reconnected to server: " + event.getConnection()))
                .build();

        return Nats.connect("nats://localhost:4222", options);
    }

}
