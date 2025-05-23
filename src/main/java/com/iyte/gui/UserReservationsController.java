package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.model.Reservation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserReservationsController {
    @FXML private TableView<Reservation> resTable;

    public void init(String username) {
        TableColumn<Reservation,String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        TableColumn<Reservation,String> evCol = new TableColumn<>("Event");
        evCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        TableColumn<Reservation,Number> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        resTable.getColumns().setAll(idCol,evCol,qtyCol);
        resTable.setItems(FXCollections.observableArrayList(ServiceLocator.SERVICE.reservationsOf(username)));
    }
}