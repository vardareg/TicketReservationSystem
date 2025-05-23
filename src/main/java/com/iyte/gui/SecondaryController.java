package com.iyte.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private void switchToPrimary(ActionEvent event) throws Exception {
        Parent primaryRoot = FXMLLoader.load(
            getClass().getResource("/com/iyte/gui/secondary.fxml")
        );
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(primaryRoot));
    }
}
