package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.model.Event;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class EventListController {
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private DatePicker fromDate, toDate;

    @FXML
    public void initialize() {
        TableColumn<Event, String> nameCol = new TableColumn<>("Event");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Event, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Event, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventTable.getColumns().setAll(nameCol, dateCol, locCol);
        refresh();
    }

    @FXML
    private void refresh() {
        eventTable.setItems(FXCollections.observableArrayList(ServiceLocator.SERVICE.getAllEvents()));
    }

    @FXML
    private void handleSearch() {
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();
        eventTable.setItems(FXCollections.observableArrayList(
                ServiceLocator.SERVICE.searchEvents(
                        from == null ? LocalDate.MIN : from,
                        to == null ? LocalDate.MAX : to)));
    }

    @FXML
    private void handleReserve() throws Exception {
        Event sel = eventTable.getSelectionModel().getSelectedItem();
        if (sel == null)
            return;
        FXMLLoader fx = new FXMLLoader(getClass().getResource("reserve_ticket.fxml"));
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setScene(new Scene(fx.load()));
        ((ReserveTicketController) fx.getController()).init(sel, "guest");
        dlg.showAndWait();
    }

    @FXML
    private void handleNewEvent() throws IOException {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("firm_create_event.fxml"));
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setScene(new Scene(fx.load()));
        dlg.setTitle("Create New Event");
        dlg.showAndWait();
        refresh(); // reload table after you potentially added an event
    }
}