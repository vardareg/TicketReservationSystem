package com.iyte.ticketsystem.cart;

import java.util.ArrayList;
import java.util.List;

public final class Cart {
    private static final Cart INSTANCE = new Cart();
    private final List<CartItem> items = new ArrayList<>();
    private Cart() {}

    public static Cart get()             { return INSTANCE; }
    public List<CartItem> items()        { return items; }
    public void clear()                  { items.clear(); }
    public int size()                    { return items.size(); }
}
