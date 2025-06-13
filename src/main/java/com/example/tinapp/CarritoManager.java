package com.example.tinapp;

public class CarritoManager {
    private static CarritoManager instance;
    private ListaSimpleCarrito carrito;

    private CarritoManager() {
        carrito = new ListaSimpleCarrito();
    }

    public static CarritoManager getInstance() {
        if (instance == null) {
            instance = new CarritoManager();
        }
        return instance;
    }

    public ListaSimpleCarrito getCarrito() {
        return carrito;
    }

    public void setCarrito(ListaSimpleCarrito carrito) {
        this.carrito = carrito;
    }
}