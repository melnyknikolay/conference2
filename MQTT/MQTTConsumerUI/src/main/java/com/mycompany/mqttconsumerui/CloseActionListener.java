package com.mycompany.mqttconsumerui;

import javafx.event.Event;
import javafx.event.EventHandler;

public class CloseActionListener implements EventHandler {
    private MainApp mainApp;

    public CloseActionListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            if (mainApp.getDataConsumer() != null) {
                mainApp.getDataConsumer().close();
                mainApp.setDataConsumer(null);
            }
            mainApp.updateState(true);
        } catch(Exception ex){
            MainApp.showErrorMesaage(ex);
            throw new RuntimeException(ex);
        }
    }

}
