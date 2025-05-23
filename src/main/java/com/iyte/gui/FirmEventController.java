package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.model.Event;
import com.iyte.ticketsystem.model.TicketCategory;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FirmEventController {
    @FXML private TextField nameField;
    @FXML private DatePicker datePicker;
    @FXML private TextField locationField;
    @FXML private TextField typeField;       // <-- new
    @FXML private TextField catNameField;
    @FXML private TextField priceField;
    @FXML private TextField capField;

    @FXML private void save() {
        try {
            String name     = nameField.getText();
            LocalDate date  = datePicker.getValue();
            String location = locationField.getText();
            String type     = typeField.getText();     // <—

            Event e = new Event(name, date, location, type);

            // You can allow multiple categories by looping here instead of single:
            e.addCategory(new TicketCategory(
                catNameField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(capField.getText())
            ));

            ServiceLocator.SERVICE.addEvent(e);
            close();
        } catch (Exception ex) {
            // show error to user instead of dumping it in nameField
            nameField.setText("Error: " + ex.getMessage());
        }
    }

    @FXML private void close() {
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
