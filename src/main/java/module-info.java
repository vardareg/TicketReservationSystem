module ticketreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens com.iyte.gui to javafx.fxml;
    opens com.iyte.ticketsystem.model to javafx.base; // allow JavaFX bean introspection
    
    exports com.iyte.ticketsystem;
    exports com.iyte.gui;
    exports com.iyte.ticketsystem.model;
}
