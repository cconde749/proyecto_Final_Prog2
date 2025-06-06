package com.example.tinapp;

public class UserManager {
    private static UserManager instance;
    private ListaCircularUsuarios usuarios;

    private UserManager() {
        usuarios = new ListaCircularUsuarios();
        // Cargar usuarios iniciales (Administrador)
        usuarios.insertar("admin@tinapp.com", "Admin", "admin123");
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public ListaCircularUsuarios getUsuarios() {
        return usuarios;
    }

    // Métodos adicionales según necesidad
    public boolean autenticar(String email, String password) {
        return usuarios.autenticar(email, password);
    }

    public boolean registrar(String email, String nombre, String password) {
        if (usuarios.existeUsuario(email)) {
            return false;
        }
        usuarios.insertar(email, nombre, password);
        return true;
    }
}
