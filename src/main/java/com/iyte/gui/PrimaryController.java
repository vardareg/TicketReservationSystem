package com.iyte.gui;

import com.iyte.ticketsystem.ServiceLocator;
import com.iyte.ticketsystem.model.Event;
import com.iyte.ticketsystem.model.TicketCategory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PrimaryController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> nameCol;
    @FXML
    private TableColumn<Event, LocalDate> dateCol;
    @FXML
    private TableColumn<Event, String> locCol;
    @FXML
    private TableColumn<Event, String> typeCol;
    @FXML
    private TableColumn<Event, Number> availCol;

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        availCol.setCellValueFactory(cell -> new SimpleIntegerProperty(
                cell.getValue()
                        .getCategories()
                        .stream()
                        .mapToInt(TicketCategory::getAvailable)
                        .sum()));

        // show *all* events on startup:
        eventTable.setItems(FXCollections.observableArrayList(
                ServiceLocator.SERVICE.getAllEvents()));
    }

    @FXML
    private void onSearch(ActionEvent e) {
        LocalDate d = datePicker.getValue();
        if (d == null) {
            eventTable.setItems(FXCollections.observableArrayList(
                    ServiceLocator.SERVICE.getAllEvents()));
        } else {
            eventTable.setItems(FXCollections.observableArrayList(
                    ServiceLocator.SERVICE.searchEvents(d, d)));
        }
    }

    @FXML
    private void onReserve(ActionEvent e) throws IOException {
        Event sel = eventTable.getSelectionModel().getSelectedItem();
        if (sel == null)
            return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("reserve_ticket.fxml"));
        Parent root = loader.load();

        // initialize the dialog controller
        ReserveTicketController ctrl = loader.getController();
        ctrl.init(sel, "guest");

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(root));
        dialog.setTitle("Reserve Tickets");
        dialog.showAndWait();
    }

}
