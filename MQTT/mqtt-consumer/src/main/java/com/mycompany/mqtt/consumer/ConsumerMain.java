/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqtt.consumer;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author denis
 */
public class ConsumerMain {
    
    public static void main(String[] args) throws Exception {
        IMqttClient consumer = new MqttClient("tcp://127.0.0.1:1883", "userIdFootballUAConsumer");
        consumer.connect();
        consumer.subscribe("mydata/temperature", new IMqttMessageListener(){
            
            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception {
                byte[] data = mm.getPayload();
                System.out.println(new String(data));
            }
            
        });
    }
    
}
