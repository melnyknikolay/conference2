/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//-DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId=javafx -DarchetypeVersion=0.6

package com.mycompany.mqtt.producer;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author denis
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        IMqttClient publisher = new MqttClient("tcp://localhost:1883", "producerA");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
        //publisher.publish("topicname", , 1, true);
        MqttMessage msg = new MqttMessage((System.currentTimeMillis()+"hello").getBytes());
        msg.setQos(0);
        msg.setRetained(false);
        publisher.publish("engineFootballUA/temperatureFootballUA", msg);
        
    }

}
