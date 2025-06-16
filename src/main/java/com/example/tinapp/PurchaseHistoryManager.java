// PurchaseHistoryManager.java
package com.example.tinapp;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryManager {
    private static PurchaseHistoryManager instance;
    private List<Purchase> historial;

    private PurchaseHistoryManager() {
        historial = new ArrayList<>();
    }

    public static PurchaseHistoryManager getInstance() {
        if (instance == null) instance = new PurchaseHistoryManager();
        return instance;
    }

    public void agregarCompra(Purchase p) {
        historial.add(p);
    }

    public List<Purchase> getHistorial() {
        return historial;
    }
}
