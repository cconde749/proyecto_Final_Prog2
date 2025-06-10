package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class CatalogoController {

    @FXML
    private GridPane productsGrid;

    private ListaDobleProductos listaProductos;

    @FXML private Label usuario_label;  // Asegúrate de que exista en el FXML

    private String emailUsuario;

    public void setUsuario(String email) {
        this.emailUsuario = email;
        if (usuario_label != null) {
            usuario_label.setText("Bienvenido, " + emailUsuario);
        }
    }

    private void addProdcts(){
        listaProductos.agregarProducto(new Producto("1", "Laptop", "Laptop gamer", 2200, "/com/images/products/laptop.png"));
        listaProductos.agregarProducto(new Producto("2", "Mouse", "Mouse inalámbrico", 30, "/com/images/products/mouse.png"));
        listaProductos.agregarProducto(new Producto("3", "Diademas", "Diademas RGB", 50, "/com/images/products/diademas.png"));
        listaProductos.agregarProducto(new Producto("4", "Teclado", "Teclado mecánico", 80, "/com/images/products/Keyboard.png"));
        listaProductos.agregarProducto(new Producto("5", "iPhone", "iPhone 14 Pro", 999, "/com/images/products/IPhone.png"));
    }

    public void setListaProductos(ListaDobleProductos listaProductos) {

        this.listaProductos = listaProductos;
        addProdcts();
        mostrarProductos();
    }

    public void mostrarProductos() {
        System.out.println("Total productos a mostrar: " + listaProductos.obtenerTodos().size());
        productsGrid.getChildren().clear();  // Limpiar por si se vuelve a llamar

        int columna = 0;
        int fila = 0;

        for (Producto producto : listaProductos.obtenerTodos()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tinapp/product_card.fxml"));
                AnchorPane card = loader.load();


                ProductCardController cardController = loader.getController();
                cardController.setData(producto);


                productsGrid.add(card, columna, fila);

                columna++;
                if (columna == 3) {
                    columna = 0;
                    fila++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
