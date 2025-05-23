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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.time.LocalDate;

public final class PrimaryController {

    /* ------------ search controls ------------ */
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtLocation;
    @FXML
    private ComboBox<String> cmbType;

    /* ------------ table & columns ------------ */
    @FXML
    private TableView<Event> tblEvents;
    @FXML
    private TableColumn<Event, String> colName;
    @FXML
    private TableColumn<Event, LocalDate> colDate;
    @FXML
    private TableColumn<Event, String> colLoc;
    @FXML
    private TableColumn<Event, String> colType;
    @FXML
    private TableColumn<Event, Number> colAvail;

    /* ------------ action buttons ------------ */
    @FXML
    private Button btnReserve;
    @FXML
    private Button btnNewEvent;
    @FXML
    private Button btnDelete;

    /* ------------ state ------------ */
    private String currentUser = "guest";
    private boolean firmMode = false;

    /* ----------- initial GUI setup ----------- */
    @FXML
    public void initialize() {
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colDate.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDate()));
        colLoc.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        colAvail.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCategories()
                .stream()
                .mapToInt(TicketCategory::getAvailable)
                .sum()));

        cmbType.getItems().addAll("Concert", "Theatre", "Conference", ""); // + blank entry
        cmbType.getSelectionModel().selectLast();

        refreshTable();
    }

    /* ----------- called from LoginController ----------- */
    public void setCurrentUser(String user, boolean isFirm) {
        this.currentUser = user;
        this.firmMode = isFirm;
        btnNewEvent.setVisible(isFirm);
        btnDelete.setVisible(isFirm);
    }

    /* ----------- UI actions ----------- */
    @FXML
    private void handleSearch(ActionEvent e) {
        refreshTable();
    }

    @FXML
    private void handleReserve(ActionEvent e) throws IOException {
        Event sel = tblEvents.getSelectionModel().getSelectedItem();
        if (sel == null)
            return;

        FXMLLoader fx = new FXMLLoader(getClass().getResource("reserve_ticket.fxml"));
        Parent ui = fx.load();
        fx.<ReserveTicketController>getController().init(sel, currentUser);

        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setScene(new Scene(ui));
        dlg.setTitle("Reserve Tickets");
        dlg.showAndWait();

        refreshTable(); // seats may have changed
    }

    @FXML
    private void handleNewEvent() throws IOException {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("firm_create_event.fxml"));
        Parent ui = fx.load();

        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setScene(new Scene(ui));
        dlg.setTitle("Create Event");
        dlg.showAndWait();

        refreshTable();
    }

    @FXML
    private void handleDeleteEvent() {
        Event sel = tblEvents.getSelectionModel().getSelectedItem();
        if (sel == null)
            return;
        ServiceLocator.SERVICE.deleteEvent(sel);
        refreshTable();
    }

    /* ----------- helpers ----------- */
    private void refreshTable() {
        LocalDate d = dpDate.getValue();
        String loc = txtLocation.getText().trim();
        String type = cmbType.getValue();

        tblEvents.setItems(FXCollections.observableArrayList(
                ServiceLocator.SERVICE.searchEvents(
                        d == null ? LocalDate.MIN : d,
                        d == null ? LocalDate.MAX : d,
                        loc,
                        type)));
    }
}
