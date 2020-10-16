package com.mycompany.mqttproducerui;

import javafx.event.Event;
import javafx.event.EventHandler;

public class SendActionListener implements EventHandler {

    private MainApp mainApp;

    public SendActionListener(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            String payload = mainApp.getMessageBodyArea().getText().trim();
            mainApp.getDataProducer().publish(payload);
            mainApp.getRecievedArea().appendText("Message was sent\n");
        }catch(Exception ex){
            MainApp.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }
}
