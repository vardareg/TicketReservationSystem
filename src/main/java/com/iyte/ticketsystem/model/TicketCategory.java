package com.iyte.ticketsystem.model;

public class TicketCategory {
    private final String categoryName;
    private final double price;
    private final int capacity;
    private int sold = 0;

    public TicketCategory(String categoryName, double price, int capacity) {
        this.categoryName = categoryName;
        this.price = price;
        this.capacity = capacity;
    }

    // ---------- getters ----------
    public String getCategoryName() {
        return categoryName;
    }

    public double getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSold() {
        return sold;
    }

    public int getAvailable() {
        return capacity - sold;
    }

    public void sell(int qty) {
        if (sold + qty > capacity)
            throw new IllegalStateException("Over‑sell");
        sold += qty;
    }

    @Override
    public String toString() {
        return getCategoryName();
    }
}