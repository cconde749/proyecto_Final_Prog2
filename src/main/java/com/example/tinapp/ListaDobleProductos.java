package com.example.tinapp;

import java.util.ArrayList;
import java.util.List;

public class ListaDobleProductos {
    private NodoProducto cabeza;
    private NodoProducto cola;
    private int tamaño;

    public ListaDobleProductos() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    // Verificar si la lista está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }

    // Obtener tamaño de la lista
    public int getTamaño() {
        return tamaño;
    }

    // Agregar producto al final de la lista
    public void agregarProducto(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);

        if (estaVacia()) {
            cabeza = cola = nuevoNodo;
        } else {
            nuevoNodo.setAnterior(cola);
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        tamaño++;
    }

    // Eliminar producto por ID
    public boolean eliminarProducto(String id) {
        if (estaVacia()) return false;

        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.getProducto().getId().equals(id)) {
                if (actual == cabeza && actual == cola) { // Único nodo
                    cabeza = cola = null;
                } else if (actual == cabeza) { // Es el primer nodo
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                } else if (actual == cola) { // Es el último nodo
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                } else { // Nodo intermedio
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // Buscar producto por ID
    public Producto buscarPorId(String id) {
        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.getProducto().getId().equals(id)) {
                return actual.getProducto();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // Obtener todos los productos como lista (para compatibilidad)
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        NodoProducto actual = cabeza;

        while (actual != null) {
            productos.add(actual.getProducto());
            actual = actual.getSiguiente();
        }

        return productos;
    }

    // Obtener producto en posición específica
    public Producto obtenerEnPosicion(int index) {
        if (index < 0 || index >= tamaño) return null;

        NodoProducto actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getProducto();
    }

    // Método adicional: actualizar producto
    public boolean actualizarProducto(String id, Producto productoActualizado) {
        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.getProducto().getId().equals(id)) {
                actual.setProducto(productoActualizado);
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
