package com.mycompany.mqttconsumerui;

import com.jpro.webapi.JProApplication;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ThreadPoolExecutor;


public class MainApp extends JProApplication {
    public static final String BROKER_URL = "tcp://localhost:1883";
    private Label clientIdLable;
    private TextField clientIdField;
    private Label topicLable;
    private TextField topicField;
    private Button connectBtn;
    private Button disconnectBtn;
    private Button closeBtn;
    private TextArea messageArea;
    private ConnectActionListener connectActionListener = new ConnectActionListener(this);
    private CloseActionListener closeActionListener = new CloseActionListener(this);
    private DisconnectActionListener disconnectActionListener = new DisconnectActionListener(this);
    private DataConsumer dataConsumer;
    private MessageListener messageListener = new MessageListener(this);


    @Override
    public void start(Stage stage) throws Exception {
        clientIdLable = new Label("ClientId");
        clientIdField = new TextField("userConsumer");
        topicLable = new Label("Topic");
        topicField = new TextField("mydata/temperature");
        connectBtn = new Button("Connect");
        connectBtn.setOnAction(connectActionListener);
        disconnectBtn = new Button("Disconnect");
        disconnectBtn.setOnAction(disconnectActionListener);

        closeBtn = new Button("Close");
        closeBtn.setOnAction(closeActionListener);

        messageArea = new TextArea();

        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.getChildren().addAll(connectBtn, disconnectBtn, closeBtn);

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(clientIdLable, clientIdField, topicLable, topicField, hbox, messageArea);
        updateState(true);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            if (dataConsumer != null) {
                try {
                    dataConsumer.close();
                } catch (Exception ex) {
                    showErrorMesaage(ex);
                    throw new RuntimeException(ex);
                }
            }
        });
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

    public void updateState(boolean isConnected) {
        clientIdField.setDisable(!isConnected);
        topicField.setDisable(!isConnected);
        connectBtn.setDisable(!isConnected);


        messageArea.setDisable(isConnected);
        disconnectBtn.setDisable(isConnected);
        closeBtn.setDisable(isConnected);

        //disconnectBtn.setDisable(!isConnected);
        //closeBtn.setDisable(!isConnected);
    }

    public TextArea getMessageArea() {
        return messageArea;
    }

    public TextField getClientIdField() {
        return clientIdField;
    }

    public TextField getTopicField() {
        return topicField;
    }

    public DataConsumer getDataConsumer() {
        return dataConsumer;
    }

    public void setDataConsumer(DataConsumer dataConsumer) {
        this.dataConsumer = dataConsumer;
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public static void showErrorMesaage(Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}
