package com.example.tinapp;

public class NodoUsuario {
    private String email;
    private String nombre;
    private String password;
    private NodoUsuario siguiente;

    public NodoUsuario(String email, String nombre, String password) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.siguiente = this; // Apunta a sí mismo inicialmente
    }

    public NodoUsuario getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoUsuario siguiente) {
        this.siguiente = siguiente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
