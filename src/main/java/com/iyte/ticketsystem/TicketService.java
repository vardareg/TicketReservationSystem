package com.iyte.ticketsystem;

import com.iyte.ticketsystem.util.FileHelper;
import com.iyte.ticketsystem.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {
    private final Map<String, User> users = new HashMap<>();
    private final List<Event> events = new ArrayList<>();

    public TicketService() {
        FileHelper.loadAll(users, events);
    }

    // --------------------------- Query API ---------------------------
    public List<Event> getAllEvents() {
        return events;
    }

    public List<Event> searchEvents(LocalDate from, LocalDate to) {
        return events.stream()
                .filter(e -> !e.getDate().isBefore(from) && !e.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public List<Reservation> reservationsOf(String username) {
        return Optional.ofNullable(users.get(username))
                .map(User::getReservations)
                .orElse(List.of());
    }

    // --------------------------- Command API -------------------------
    public void addEvent(Event e) {
        events.add(e);
    }

    public Reservation reserve(String username, Event e, String categoryName, int qty) {
        TicketCategory cat = e.category(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (cat.getAvailable() < qty)
            throw new IllegalStateException("Not enough tickets left");
        cat.sell(qty);
        Reservation r = new Reservation(username, e, cat, qty);
        users.computeIfAbsent(username, User::new).addReservation(r);
        return r;
    }

    public void save() {
        FileHelper.saveAll(users, events);
    }
}