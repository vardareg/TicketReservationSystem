package com.iyte.ticketsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private final String reservationId = UUID.randomUUID().toString();
    private final String username; // may be "guest" if no login
    private final String eventName;
    private final String categoryName;
    private final int quantity;
    private final double price;
    private final LocalDateTime ts = LocalDateTime.now();

    public Reservation(String username, Event e, TicketCategory c, int quantity) {
        this.username = username;
        this.eventName = e.getName();
        this.categoryName = c.getCategoryName();
        this.quantity = quantity;
        this.price = c.getPrice() * quantity;
    }

    // ---------- getters ----------
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

    public LocalDateTime getTs() {
        return ts;
    }
}