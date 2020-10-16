package com.mycompany.mqttproducerui;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static final String BROKER_URL = "tcp://localhost:1883";
    private VBox vbox;
    private Label clientIdLabel;
    private TextField clientIdField;
    private Label topicLabel;
    private TextField topicField;
    private HBox hbox;
    private Button connectBtn;
    private Button disconnectBtn;
    private Button closeBtn;
    private Label producerActionLabel;
    private TextArea messageBodyArea;
    private Button sendBtn;
    private Label receivedLabel;
    private TextArea recievedArea;
    private ConnectActionListener connectActionListener = new ConnectActionListener(this);
    private DisconnectActionListener disconnectActionListener = new DisconnectActionListener(this);
    private CloseActionListener closeActionListener = new CloseActionListener(this);
    private SendActionListener sendActionListener = new SendActionListener(this);
    private DataProducer dataProducer;

    @Override
    public void start(Stage stage) throws Exception {
        vbox = new VBox(15);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setAlignment(Pos.CENTER);

        clientIdLabel = new Label("ClinetId");
        clientIdField = new TextField("DenisId");

        topicLabel = new Label("Topic");
        topicField = new TextField("mydata/temperature");
        hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);

        connectBtn = new Button("Connect");
        connectBtn.setOnAction(connectActionListener);
        disconnectBtn = new Button("Disconnect");
        disconnectBtn.setOnAction(disconnectActionListener);
        closeBtn = new Button("Close");
        closeBtn.setOnAction(closeActionListener);

        hbox.getChildren().addAll(connectBtn, disconnectBtn, closeBtn);

        producerActionLabel = new Label("Put message payload:");
        messageBodyArea = new TextArea();
        sendBtn = new Button("Send");
        sendBtn.setOnAction(sendActionListener);
        receivedLabel = new Label("Data from server: ");
        recievedArea = new TextArea();

        vbox.getChildren().addAll(clientIdLabel, clientIdField, topicLabel, topicField, hbox, producerActionLabel, messageBodyArea, sendBtn, receivedLabel, recievedArea);
        updateState(true);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("MQTT Producer");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            if (getDataProducer() != null) {
                try {
                    getDataProducer().close();
                } catch (Exception ex) {
                    showErrorMessage(ex);
                }
            }
        });
    }

    public void updateState(boolean isConnected) {
        disconnectBtn.setDisable(isConnected);
        sendBtn.setDisable(isConnected);
        closeBtn.setDisable(isConnected);

        messageBodyArea.setDisable(isConnected);
        recievedArea.setDisable(isConnected);

        topicField.setDisable(!isConnected);
        clientIdField.setDisable(!isConnected);
        connectBtn.setDisable(!isConnected);

        closeBtn.setDisable(false);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }

    public void setDataProducer(DataProducer dataProducer) {
        this.dataProducer = dataProducer;
    }

    public TextField getClientIdField() {
        return clientIdField;
    }

    public TextField getTopicField() {
        return topicField;
    }

    public TextArea getMessageBodyArea() {
        return messageBodyArea;
    }

    public TextArea getRecievedArea() {
        return recievedArea;
    }


    public static void showErrorMessage(Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }


}
