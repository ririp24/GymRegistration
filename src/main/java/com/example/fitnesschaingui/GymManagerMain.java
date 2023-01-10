package com.example.fitnesschaingui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Gym Manager GUI.
 * Launches the Java FX application.
 * @author Albert Zou, Rishabh Patel
 */
public class GymManagerMain extends Application {
    /**
     * Starts the application, setting up the stage and scene.
     * @param stage received from the launching of the application.
     */
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 600);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Gym Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application.
     * @param args from the command line
     */
    public static void main(String[] args) {
        launch();
    }
}
