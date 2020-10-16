package com.app01;

import io.nats.client.*;

import java.io.IOException;

public class AsyncConsumerWith2ListenersWithReplyTo {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        AsyncSubscription asyncSubscription = natsConnection.subscribe("topic1", new MessageHandler() {
            @Override
            public void onMessage(Message message) {
                System.out.println("L1#"+hashCode()+":"+message);
                if(message.getReplyTo()!=null){
                    try {
                        natsConnection.publish(message.getReplyTo(), "L1: ACK".getBytes());
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                /*try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        });

        AsyncSubscription asyncSubscription2 = natsConnection.subscribe("topic1", new MessageHandler() {
            @Override
            public void onMessage(Message message) {
                System.out.println("L2#"+hashCode()+":"+message);
                if(message.getReplyTo()!=null){
                    try {
                        natsConnection.publish(message.getReplyTo(), "L2: ACK".getBytes());
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
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
