package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.cart.Cart;
import com.iyte.ticketsystem.cart.CartItem;
import com.iyte.ticketsystem.model.Event;
import com.iyte.ticketsystem.model.TicketCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.stage.Stage;

public class ReserveTicketController {

    // store these so onConfirm can see them:
    private Event event;
    private String username;

    @FXML
    private Label lblEvent;
    @FXML
    private ComboBox<TicketCategory> categoryBox;
    @FXML
    private Spinner<Integer> qtySpinner;

    /** Called by PrimaryController before showing the dialog */
    public void init(Event e, String user) {
        this.event = e;
        this.username = user;

        lblEvent.setText(e.getName());
        categoryBox.getItems().setAll(e.getCategories());

        // figure out the max available across all categories
        int maxAvail = e.getCategories().stream()
                .mapToInt(cat -> cat.getAvailable())
                .max()
                .orElse(1);

        SpinnerValueFactory<Integer> vf = new IntegerSpinnerValueFactory(1, maxAvail);
        qtySpinner.setValueFactory(vf);
    }

    @FXML
    private void onConfirm() {
        TicketCategory cat = categoryBox.getValue();
        int qty = qtySpinner.getValue();

        try {
            Cart.get().items().add(new CartItem(event, cat, qty));
            // close dialog
            ((Stage) qtySpinner.getScene().getWindow()).close();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void onCancel(ActionEvent evt) {
        ((Stage) lblEvent.getScene().getWindow()).close();
    }
}
