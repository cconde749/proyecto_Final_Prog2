package com.example.tinapp;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListaSimpleCarrito {
    private NodoItemCarrito primero;
    private int tamaño;

    private static class NodoItemCarrito {
        ItemCarrito item;
        NodoItemCarrito siguiente;

        NodoItemCarrito(ItemCarrito item) {
            this.item = item;
            this.siguiente = null;
        }
    }

    public void agregarItem(ItemCarrito item) {
        NodoItemCarrito nuevo = new NodoItemCarrito(item);

        // Verificar si el producto ya existe
        NodoItemCarrito actual = primero;
        while (actual != null) {
            if (actual.item.getProducto().getId().equals(item.getProducto().getId())) {
                actual.item.setCantidad(actual.item.getCantidad() + item.getCantidad());
                return;
            }
            actual = actual.siguiente;
        }

        // Si no existe, agregarlo al final
        if (primero == null) {
            primero = nuevo;
        } else {
            actual = primero;
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

    public ObservableList<ItemCarrito> obtenerTodosItems() {
        List<ItemCarrito> items = new ArrayList<>();
        NodoItemCarrito actual = primero;

        while (actual != null) {
            items.add(actual.item);
            actual = actual.siguiente;
        }

        return (ObservableList<ItemCarrito>) items;
    }

    public double calcularTotal() {
        double total = 0;
        NodoItemCarrito actual = primero;

        while (actual != null) {
            total += actual.item.getSubtotal();
            actual = actual.siguiente;
        }

        return total;
    }

    public int getTamaño() {
        return tamaño;
    }
}