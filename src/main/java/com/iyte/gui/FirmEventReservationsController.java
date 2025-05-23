package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.model.Event;
import com.iyte.ticketsystem.model.Reservation;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public final class FirmEventReservationsController {

    @FXML private Label lblEvent;
    @FXML private TableView<Reservation>            tbl;
    @FXML private TableColumn<Reservation,String>   colUser;
    @FXML private TableColumn<Reservation,String>   colCat;
    @FXML private TableColumn<Reservation,Integer>  colQty;
    @FXML private TableColumn<Reservation,String>   colId;

    public void init(Event event) {
        lblEvent.setText(event.getName());

        colUser.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsername()));
        colCat .setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory().getCategoryName()));
        colQty .setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()).asObject());
        colId  .setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReservationId()));

        tbl.setItems(FXCollections.observableArrayList(
                ServiceLocator.SERVICE.reservationsForEvent(event)));
    }
}
