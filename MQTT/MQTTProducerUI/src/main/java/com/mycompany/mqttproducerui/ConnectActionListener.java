/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqttproducerui;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author denis
 */
public class ConnectActionListener implements EventHandler {
    private MainApp mainApp;

    public ConnectActionListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            mainApp.updateState(false);
            String clientId = mainApp.getClientIdField().getText().trim();
            String topicName = mainApp.getTopicField().getText().trim();
            DataProducer dp = new DataProducer(MainApp.BROKER_URL, clientId, topicName);
            dp.connect();
            mainApp.setDataProducer(dp);
            
        } catch (Exception ex) {
           MainApp.showErrorMessage(ex);
           throw new RuntimeException(ex);
        }
    }
    
    
    
}
