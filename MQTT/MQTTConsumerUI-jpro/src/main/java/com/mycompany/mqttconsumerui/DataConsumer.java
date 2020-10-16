package com.mycompany.mqttconsumerui;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DataConsumer {
    private String url;
    private String clientId;
    private String topic;
    private IMqttClient consumer;
    private MessageListener messageListener;

    public DataConsumer(String url, String clientId, String topic, MessageListener messageListener) throws Exception {
        this.url = url;
        this.clientId = clientId;
        this.topic = topic;
        this.messageListener = messageListener;
        consumer = new MqttClient(url, clientId);
    }

    public void connect() throws Exception {
        System.out.println("topic="+topic);
        consumer.connect();
        consumer.subscribe(topic, messageListener);
    }

    public void disconnect() throws Exception {
        if(consumer.isConnected()){
            consumer.disconnect();
        }
    }

    public void close() throws Exception {
        if(consumer.isConnected()){
            consumer.disconnect();
        }
        consumer.close();
    }
}
