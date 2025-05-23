package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new FXMLLoader(App.class.getResource("primary.fxml")).load());
        stage.setTitle("Ticket Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        ServiceLocator.SERVICE.save();
    }

    public static void main(String[] args) { launch(args); }
}