package com.iyte.ticketsystem;

import com.iyte.ticketsystem.model.*;
import com.iyte.ticketsystem.util.FileHelper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public final class TicketService {

    private final Map<String, User> users = new HashMap<>();
    private final List<Event> events = new ArrayList<>();

    public TicketService() { // load everything once
        FileHelper.loadAll(users, events);
    }

    /* ---------- read-side API ---------- */

    public List<Event> getAllEvents() {
        return events;
    }

    public List<Event> searchEvents(LocalDate from, LocalDate to,
            String location, String type) {
        return events.stream()
                .filter(e -> !e.getDate().isBefore(from) && !e.getDate().isAfter(to))
                .filter(e -> location == null || location.isBlank() ||
                        e.getLocation().equalsIgnoreCase(location))
                .filter(e -> type == null || type.isBlank() ||
                        e.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Reservation> reservationsOf(String user) {
        return Optional.ofNullable(users.get(user))
                .map(User::getReservations)
                .orElse(List.of());
    }

    /* ---------- write-side API ---------- */

    public void ensureUser(String username) {
        users.computeIfAbsent(username, User::new);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void deleteEvent(Event e) {
        events.remove(e);
    }

    public Reservation reserve(String username, Event e,
            String categoryName, int qty) {

        TicketCategory cat = e.category(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (cat.getAvailable() < qty)
            throw new IllegalStateException("Not enough tickets left");

        cat.sell(qty); // update availability
        Reservation r = new Reservation(username, e, cat, qty);
        users.computeIfAbsent(username, User::new)
                .addReservation(r);
        return r;
    }

    public void save() {
        FileHelper.saveAll(users, events);
    }
}
