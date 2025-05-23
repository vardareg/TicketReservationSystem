package com.iyte.ticketsystem.cart;

import com.iyte.ticketsystem.model.Event;
import com.iyte.ticketsystem.model.TicketCategory;

public record CartItem(Event event, TicketCategory category, int quantity) {

    public double totalPrice() {
        return category.getPrice() * quantity;
    }
}