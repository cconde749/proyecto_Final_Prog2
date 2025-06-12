package com.example.tinapp;

import java.util.List;

public class ListaSimpleCarrito {
    private NodoItemCarrito primero;
    private int tamaño;

    private class NodoItemCarrito {
        ItemCarrito item;
        NodoItemCarrito siguiente;

        NodoItemCarrito(ItemCarrito item) {
            this.item = item;
            this.siguiente = null;
        }
    }

    public void agregarItem(ItemCarrito item) {
        NodoItemCarrito nuevo = new NodoItemCarrito(item);

        if (primero == null) {
            primero = nuevo;
        } else {
            NodoItemCarrito actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }

    public boolean eliminarItem(String idProducto) {
        if (primero == null) return false;

        if (primero.item.getProducto().getId().equals(idProducto)) {
            primero = primero.siguiente;
            tamaño--;
            return true;
        }

        NodoItemCarrito actual = primero;
        while (actual.siguiente != null) {
            if (actual.siguiente.item.getProducto().getId().equals(idProducto)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamaño--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public List<ItemCarrito> obtenerTodosItems() {
        List<ItemCarrito> items = new java.util.ArrayList<>();
        NodoItemCarrito actual = primero;

        while (actual != null) {
            items.add(actual.item);
            actual = actual.siguiente;
        }

        return items;
    }

    public double calcularTotal() {
        double total = 0;
        NodoItemCarrito actual = primero;

        while (actual != null) {
            total += actual.item.getProducto().getPrecio() * actual.item.getCantidad();
            actual = actual.siguiente;
        }

        return total;
    }
}