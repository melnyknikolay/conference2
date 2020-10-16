/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app01.app03;

import io.nats.client.Connection;
import io.nats.client.Nats;

/**
 *
 * @author asusadmin
 */
public class SimpleProducer {
    
    public static void main(String[] args) throws Exception {
        Connection natsConnection = Nats.connect();
        natsConnection.publish("ttt", "Hi there!".getBytes());
        natsConnection.publish("ttt", "Hi there2!".getBytes());
        natsConnection.close();
    }
}
