package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Simple username + role login. */
public final class LoginController {

    @FXML private TextField txtUser;
    @FXML private ComboBox<String> cmbRole;     // “User” or “Firm”

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("User", "Firm");
        cmbRole.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleLogin() throws Exception {
        String user = txtUser.getText().trim();
        if (user.isBlank()) return;

        String role = cmbRole.getValue();
        if ("User".equals(role)) {
            // create on-the-fly if not present
            ServiceLocator.SERVICE.ensureUser(user);
        }

        FXMLLoader fx = new FXMLLoader(getClass().getResource("primary.fxml"));
        Stage stage   = (Stage) txtUser.getScene().getWindow();
        stage.setScene(new Scene(fx.load()));

        // pass who is signed-in to the main controller
        PrimaryController pc = fx.getController();
        pc.setCurrentUser(user, "Firm".equals(role));
    }
}
