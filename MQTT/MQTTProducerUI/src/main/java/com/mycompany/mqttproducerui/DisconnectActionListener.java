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
public class DisconnectActionListener implements EventHandler {

    private MainApp mainApp;

    public DisconnectActionListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            mainApp.updateState(true);
            mainApp.getDataProducer().disconnect();
        } catch (Exception ex) {
            MainApp.showErrorMessage(ex);
            throw new RuntimeException(ex);
        }
    }

}
