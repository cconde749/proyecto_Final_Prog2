package com.example.tinapp;

import java.util.Stack;

public class ListaDeseosManager {
    private static ListaDeseosManager instance;
    private Stack<Producto> listaDeseos;

    private ListaDeseosManager() {
        listaDeseos = new Stack<>();
    }

    public static ListaDeseosManager getInstance() {
        if (instance == null) {
            instance = new ListaDeseosManager();
        }
        return instance;
    }

    public void agregarProducto(Producto producto) {
        if (!listaDeseos.contains(producto)) {
            listaDeseos.push(producto);
        }
    }

    public Stack<Producto> obtenerListaDeseos() {
        return listaDeseos;
    }

    public void limpiarLista() {
        listaDeseos.clear();
    }
}
