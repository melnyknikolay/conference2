/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nats.avro.consumer;

import io.nats.client.AsyncSubscription;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Nats;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author denis
 */
public class ConsumerMain {

    public static void main(String[] args) throws IOException {
        Connection natsConnection = Nats.connect();

        MyTransformerBinary requestTransformer = new MyTransformerBinary("/user.avsc");
        MyTransformerBinary responseTransformer = new MyTransformerBinary("/user_1.avsc");

        AsyncSubscription asyncSubscription = natsConnection.subscribe("ttt/", new MessageHandler() {

            @Override
            public void onMessage(Message message) {
                System.out.println("Consumer.onMessage(): " + message);
                System.out.println("after transformation: " + requestTransformer.deserialize(message.getData()));
                try {
                    natsConnection.publish(message.getReplyTo(), responseTransformer.serialize(getResponseMap()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        );

    }

    private static Map<String, Object> getResponseMap() {
        Map<String, Object> map = new HashMap();
        map.put("status_code", 200);
        map.put("message", "success");
        return map;
    }

}
