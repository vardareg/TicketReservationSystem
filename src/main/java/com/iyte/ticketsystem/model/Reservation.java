package com.iyte.ticketsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private final String reservationId = UUID.randomUUID().toString();
    private final String username; // "guest" if not logged-in
    private final String eventName;
    private final String categoryName;
    private final int quantity;
    private final double price;
    private final LocalDateTime ts = LocalDateTime.now();

    private final TicketCategory category; // ← stored reference

    public Reservation(String username, Event e,
            TicketCategory c, int quantity) {

        this.username = username;
        this.eventName = e.getName();
        this.categoryName = c.getCategoryName();
        this.quantity = quantity;
        this.price = c.getPrice() * quantity;

        this.category = c; // ← **INITIALISE HERE**
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

    public String getEventName() {
        return eventName;
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
