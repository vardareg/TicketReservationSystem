package com.iyte.ticketsystem.model;

import java.time.LocalDate;
import java.util.*;

public class Event {
    private final String name;
    private final LocalDate date;
    private final String location;
    private final String type;
    private final List<TicketCategory> categories = new ArrayList<>();

    public Event(String name, LocalDate date, String location, String type) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.type = type;
    }

    // ---------- getters ----------
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public List<TicketCategory> getCategories() {
        return categories;
    }

    public Optional<TicketCategory> category(String name) {
        return categories.stream()
                .filter(c -> c.getCategoryName().equalsIgnoreCase(name))
                .findFirst();
    }

    public double firstPrice() {
        return categories.isEmpty() ? 0 : categories.get(0).getPrice();
    }

    // helper
    public void addCategory(TicketCategory c) {
        categories.add(c);
    }
}