// Purchase.java
package com.example.tinapp;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {
    private LocalDateTime fecha;
    private double total;
    private List<ItemCarrito> items;

    public Purchase(LocalDateTime fecha, double total, List<ItemCarrito> items) {
        this.fecha = fecha;
        this.total = total;
        this.items = items;
    }
    public LocalDateTime getFecha() { return fecha; }
    public double getTotal() { return total; }
    public List<ItemCarrito> getItems() { return items; }
}
