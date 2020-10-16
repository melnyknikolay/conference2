package com.mycompany.mqttconsumerui;

import javafx.event.Event;
import javafx.event.EventHandler;

public class ConnectActionListener implements EventHandler {
    private MainApp mainApp;


    public ConnectActionListener(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    @Override
    public void handle(Event event) {
        try {
            String clientId = mainApp.getClientIdField().getText();
            String topic = mainApp.getTopicField().getText();
            if (mainApp.getDataConsumer() == null) {
                mainApp.setDataConsumer(new DataConsumer(MainApp.BROKER_URL, clientId, topic, mainApp.getMessageListener()));
            }
            mainApp.getDataConsumer().connect();
            mainApp.updateState(false);
        } catch (Exception ex){
            MainApp.showErrorMesaage(ex);
            throw new RuntimeException(ex);
        }
    }

}
