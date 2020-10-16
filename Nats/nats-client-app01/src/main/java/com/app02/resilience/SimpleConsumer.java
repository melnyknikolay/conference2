package com.app02.resilience;

import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleConsumer {
    private final static Logger log = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) throws IOException {
        Connection natsConnection = initConnection();
        System.out.println(natsConnection.getExceptionHandler());

        natsConnection.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void onException(NATSException e) {
               System.out.println("onException2(); [e="+e+"]");
               e.printStackTrace();
            }
        });

        AsyncSubscription asyncSubscription = natsConnection.subscribe("simpleTopic", new MessageHandler() {
                    @Override
                    public void onMessage(Message message) {
                        System.out.println("onMessage(): "+message);

            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(message);*/
                        //message.getReplyTo().getBytes();
                        if (1 < 2) {
                         //   throw new RuntimeException("Test");
                        }

                    }
                }
        );


    }


    private static Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .errorCb(new ExceptionHandler() {
                    @Override
                    public void onException(NATSException e) {
                        System.out.println("onException(); [e=" + e + "]");
                    }
                })

                //.errorCb(e->{System.out.println("onException(); [e="+e+"]");})
                .name("myconnection")
                .disconnectedCb(event -> System.out.println("Channel disconnected: " + event.getConnection()))
                .reconnectedCb(event -> System.out.println("Reconnected to server: " + event.getConnection()))
                .build();


        return Nats.connect("nats://localhost:4222", options);
    }
}
