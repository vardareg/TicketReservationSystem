package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.cart.Cart;
import com.iyte.ticketsystem.cart.CartItem;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public final class CheckoutController {

    @FXML
    private TableView<CartItem> tbl;
    @FXML
    private TableColumn<CartItem, String> colEvent;
    @FXML
    private TableColumn<CartItem, String> colCat;
    @FXML
    private TableColumn<CartItem, Integer> colQty;
    @FXML
    private TableColumn<CartItem, Double> colPrice;
    @FXML
    private TableColumn<CartItem, Double> colTotal;
    @FXML
    private Label lblGrand;

    private String currentUser;
    private Runnable afterClose;

    /* called from PrimaryController.handleCart() */
    public void init(String user, Runnable afterClose) {
        this.currentUser = user;
        this.afterClose = afterClose;

        colEvent.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().event().getName()));
        colCat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().category().getCategoryName())); // ← fixed
        colQty.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().quantity()).asObject());
        colPrice.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().category().getPrice()).asObject()); // ←
                                                                                                                    // fixed
        colTotal.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().totalPrice()).asObject());

        tbl.setItems(FXCollections.observableArrayList(Cart.get().items()));
        lblGrand.setText(String.format("Total: %.2f €",
                Cart.get().items().stream().mapToDouble(CartItem::totalPrice).sum()));
    }

    /* ---------- buttons ---------- */
    @FXML
    private void confirm() {
        Cart.get().items().forEach(i -> ServiceLocator.SERVICE.reserve(
                currentUser,
                i.event(),
                i.category().getCategoryName(),
                i.quantity()));

        Cart.get().clear();

        if (afterClose != null)
            afterClose.run(); // << notify the opener
        ((Stage) tbl.getScene().getWindow()).close();
    }

    @FXML
    private void cancel() {
        ((Stage) tbl.getScene().getWindow()).close(); // ← Stage imported
    }
}
