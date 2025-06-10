package com.example.tinapp;

public class NodoProducto {
    private Producto producto;
    private NodoProducto anterior;
    private NodoProducto siguiente;

    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.anterior = null;
        this.siguiente = null;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public NodoProducto getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoProducto anterior) {
        this.anterior = anterior;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }
}
