/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nats.fx.listener.avro;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Nats;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

/**
 *
 * @author asusadmin
 */
public class ConnectEventHandler implements EventHandler {

    private MainApp mainApp;
    private MyTransformerBinary requestTransformer = new MyTransformerBinary("/user.avsc");

    public ConnectEventHandler(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(Event event) {
        try {
            String natsUrl = mainApp.natsUrlField.getText();
            String natsSubject = mainApp.natsSubjectField.getText();
            Connection natsConnection = Nats.connect(natsUrl);
            natsConnection.subscribe(natsSubject, new MessageHandler() {

                @Override
                public void onMessage(Message message) {                    
                    Map<String, Object> map = requestTransformer.deserialize(message.getData());                    
                    Platform.runLater(() -> {
                        mainApp.textArea.appendText("\n\n" + map);
                    });
                }
            });
            mainApp.updateState(true);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

}
