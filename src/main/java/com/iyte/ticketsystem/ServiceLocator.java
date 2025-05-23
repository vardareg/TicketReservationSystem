package com.iyte.ticketsystem;

public final class ServiceLocator {
    public static final TicketService SERVICE = new TicketService();
    private ServiceLocator() {}
}