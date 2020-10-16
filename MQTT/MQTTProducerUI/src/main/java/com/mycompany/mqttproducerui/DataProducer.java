/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqttproducerui;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author denis
 */
public class DataProducer {

    private String url;
    private String clientId;
    private String topicName;
    private MqttClient publisher;

    public DataProducer(String url, String clientId, String topicName) throws Exception {
        this.url = url;
        this.clientId = clientId;
        this.topicName = topicName;
        publisher = new MqttClient(url, clientId);
    }

    public void connect() throws Exception {
       MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
    }

    public void publish(String payload) throws Exception {
        MqttMessage msg = new MqttMessage(payload.getBytes());
        msg.setQos(0);
        msg.setRetained(false);
        publisher.publish(topicName, msg);
    }

    public void disconnect() throws Exception {
        publisher.disconnect();
    }

    public void close() throws Exception {
        if(publisher.isConnected()) {
            disconnect();
        }
        publisher.close();
    }

}
