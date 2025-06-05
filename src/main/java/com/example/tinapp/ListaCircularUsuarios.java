package com.example.tinapp;

public class ListaCircularUsuarios {
    private NodoUsuario ultimo;

    public boolean estaVacia() {
        return ultimo == null;
    }

    public void insertar(String email, String nombre, String password) {
        NodoUsuario nuevo = new NodoUsuario(email, nombre, password);

        if (estaVacia()) {
            ultimo = nuevo;
        } else {
            nuevo.setSiguiente(ultimo.getSiguiente());
            ultimo.setSiguiente(nuevo) ;
            ultimo = nuevo;
        }
    }

    public boolean autenticar(String email, String password) {
        if (estaVacia()) return false;

        NodoUsuario actual = ultimo.getSiguiente();
        NodoUsuario inicio = actual;

        do {
            if (actual.getEmail().equals(email) && actual.getPassword().equals(password)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != inicio);

        return false;
    }

    public boolean existeUsuario(String email) {
        if (estaVacia()) return false;

        NodoUsuario actual = ultimo.getSiguiente();
        NodoUsuario inicio = actual;

        do {
            if (actual.getEmail().equals(email)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != inicio);

        return false;
    }
}