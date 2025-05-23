package com.iyte.ticketsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

// src/main/java/com/iyte/ticketsystem/model/Reservation.java
public class Reservation {

    private final String reservationId = UUID.randomUUID().toString();
    private final Event event; // ← NEW
    private final String username;
    private final String categoryName;
    private final int quantity;
    private final double price;
    private final LocalDateTime ts = LocalDateTime.now();
    private final TicketCategory category;

    public Reservation(String username, Event e,
            TicketCategory c, int quantity) {
        this.event = e; // ← store reference
        this.username = username;
        this.categoryName = c.getCategoryName();
        this.quantity = quantity;
        this.price = c.getPrice() * quantity;
        this.category = c;
    }

    public Event getEvent() { // ← NEW
        return event;
    }

    /* ------------ getters ------------ */
    public TicketCategory getCategory() {
        return category;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getUsername() {
        return username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
