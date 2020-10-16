/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nats.avro.producer;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author denis
 */
public class ProducerMain {

    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        MyTransformerBinary myRequestTransformer = new MyTransformerBinary("/user.avsc");
        byte[] data = myRequestTransformer.serialize(initMap());
        Message reply = natsConnection.request("ttt/", data, 10, TimeUnit.MILLISECONDS);
        System.out.println("Reply: " + reply);
        MyTransformerBinary myResponseTransformer = new MyTransformerBinary("/user_1.avsc");
        Map<String, Object> map = myResponseTransformer.deserialize(reply.getData());
        System.out.println(map);
    }

    private static Map<String, Object> initMap() {
        Map<String, Object> map = new HashMap();
        map.put("name", "Denys");
        map.put("favorite_number", 17);
        return map;
    }

}
