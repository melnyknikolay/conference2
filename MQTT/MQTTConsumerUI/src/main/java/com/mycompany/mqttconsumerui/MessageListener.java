package com.mycompany.mqttconsumerui;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageListener implements IMqttMessageListener {
    private MainApp mainApp;

    public MessageListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        byte[] data = mqttMessage.getPayload();
        //mainApp.get
        //System.out.println(new String(data));
        mainApp.getMessageArea().appendText(new String(data)+"\n");
    }
}
