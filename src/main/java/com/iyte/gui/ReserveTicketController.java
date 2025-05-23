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

        // 1️⃣ put categories into the combo-box
        categoryBox.getItems().setAll(e.getCategories());

        // 2️⃣ choose “Standard” / “Standart” if it exists
        TicketCategory defaultCat = e.getCategories().stream()
                .filter(c -> {
                    String n = c.getCategoryName().toLowerCase();
                    return n.equals("standard") || n.equals("standart");
                })
                .findFirst()
                .orElse(e.getCategories().isEmpty() ? null
                        : e.getCategories().get(0));

        categoryBox.getSelectionModel().select(defaultCat);

        // helper to (re)configure the spinner for a chosen category
        Runnable refreshSpinner = () -> {
            TicketCategory sel = categoryBox.getValue();
            int max = sel == null ? 1 : Math.max(1, sel.getAvailable());
            qtySpinner.setValueFactory(new IntegerSpinnerValueFactory(1, max, 1));
        };
        refreshSpinner.run(); // initial setup

        // 3️⃣ update spinner limits when category changes
        categoryBox.valueProperty().addListener((obs, oldV, newV) -> refreshSpinner.run());
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
