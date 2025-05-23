package com.iyte.ticketsystem.model;

import java.util.*;

public class User {
    private final String username;
    private final List<Reservation> reservations = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }
}