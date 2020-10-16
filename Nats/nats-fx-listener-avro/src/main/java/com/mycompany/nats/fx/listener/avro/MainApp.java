package com.mycompany.nats.fx.listener.avro;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    protected Label natsUrlLabel;
    protected TextField natsUrlField;
    protected Label natsSubjectLabel;
    protected TextField natsSubjectField;
    protected Button connectBtn;
    protected TextArea textArea;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15, 15, 15, 15));

        natsUrlLabel = new Label("NATS URL:");
        natsUrlField = new TextField("nats://127.0.0.1:4222");
        natsSubjectLabel = new Label("NATS topic:");
        natsSubjectField = new TextField("ttt");
        connectBtn = new Button("Connect");
        connectBtn.setOnAction(new ConnectEventHandler(this));
        textArea = new TextArea();
        root.getChildren().addAll(natsUrlLabel, natsUrlField, natsSubjectLabel, natsSubjectField, connectBtn, textArea);
        updateState(false);

        Scene scene = new Scene(root, 400, 500);

        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
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

    protected void updateState(boolean connected) {
        natsUrlField.setDisable(connected);
        connectBtn.setDisable(connected);
        natsSubjectField.setDisable(connected);
        textArea.setDisable(!connected);
    }

}
