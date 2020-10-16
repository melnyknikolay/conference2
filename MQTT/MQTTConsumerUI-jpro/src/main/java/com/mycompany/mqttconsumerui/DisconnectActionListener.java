package com.mycompany.mqttconsumerui;

import javafx.event.Event;
import javafx.event.EventHandler;

public class DisconnectActionListener implements EventHandler {
    private MainApp mainApp;

    public DisconnectActionListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            if (mainApp.getDataConsumer() != null) {
                mainApp.getDataConsumer().disconnect();
            }
            mainApp.updateState(true);
        }catch(Exception ex){
            MainApp.showErrorMesaage(ex);
            throw new RuntimeException(ex);
        }
    }

}
