package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class CatalogoController implements Initializable {
    @FXML private GridPane productsGrid;
    private ListaDobleProductos listaProductos = new ListaDobleProductos();
    private ListaSimpleCarrito carrito = new ListaSimpleCarrito();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProductosEjemplo();
        mostrarProductos();
    }

    private void cargarProductosEjemplo() {
        // Mantén tus productos existentes
        listaProductos.agregarProducto(new Producto("1", "Laptop Gamer",
                "Laptop de alto rendimiento", 1299.99, "laptop.png"));
        listaProductos.agregarProducto(new Producto("2", "Smartphone",
                "Último modelo con cámara 108MP", 799.99, "phone.png"));
        // Agrega más productos según necesites
    }

    private void mostrarProductos() {
        productsGrid.getChildren().clear();
        productsGrid.getRowConstraints().clear(); // Limpiar restricciones de fila anteriores

        int column = 0;
        int row = 1;

        for (Producto producto : listaProductos.obtenerTodos()) {
            VBox card = crearTarjetaProducto(producto);
            productsGrid.add(card, column++, row);
            GridPane.setMargin(card, new Insets(10));

            if (column == 3) {
                column = 0;
                row++;
                // Añadir restricción para la nueva fila
                RowConstraints rowConstraint = new RowConstraints();
                rowConstraint.setPrefHeight(450); // Altura aproximada de cada tarjeta
                rowConstraint.setVgrow(Priority.NEVER);
                productsGrid.getRowConstraints().add(rowConstraint);
            }
        }
    }

    private VBox crearTarjetaProducto(Producto producto) {
        VBox card = new VBox(10);
        card.getStyleClass().add("product-card");
        card.setAlignment(Pos.TOP_CENTER);

        // Imagen del producto
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream(
                    "/com/example/tinapp/images/" + producto.getImagenUrl()));
            imageView.setImage(image);
        } catch (Exception e) {
            imageView.setImage(new Image(getClass().getResourceAsStream(
                    "/com/images/products/diademas.png")));
        }
        imageView.getStyleClass().add("product-image");

        // Nombre del producto
        Label nameLabel = new Label(producto.getNombre());
        nameLabel.getStyleClass().add("product-name");

        // Descripción
        Label descLabel = new Label(producto.getDescripcion());
        descLabel.getStyleClass().add("product-desc");

        // Precio
        Label priceLabel = new Label(String.format("$%.2f", producto.getPrecio()));
        priceLabel.getStyleClass().add("product-price");

        // Controles de cantidad
        HBox quantityBox = new HBox(10);
        quantityBox.getStyleClass().add("quantity-box");

        Button decreaseBtn = new Button("-");
        decreaseBtn.getStyleClass().add("quantity-btn");

        Label quantityLabel = new Label("1");
        quantityLabel.getStyleClass().add("quantity-label");

        Button increaseBtn = new Button("+");
        increaseBtn.getStyleClass().add("quantity-btn");

        // Manejo de eventos para cantidad
        final int[] cantidad = {1};
        decreaseBtn.setOnAction(e -> {
            if (cantidad[0] > 1) cantidad[0]--;
            quantityLabel.setText(String.valueOf(cantidad[0]));
        });

        increaseBtn.setOnAction(e -> {
            cantidad[0]++;
            quantityLabel.setText(String.valueOf(cantidad[0]));
        });

        quantityBox.getChildren().addAll(decreaseBtn, quantityLabel, increaseBtn);

        // Botones de acción
        HBox actionBox = new HBox(15);
        actionBox.getStyleClass().add("action-box");

        Button addToCartBtn = new Button("Añadir al carrito");
        addToCartBtn.getStyleClass().add("add-to-cart-btn");

        Button wishlistBtn = new Button();
        wishlistBtn.getStyleClass().add("wishlist-btn");
        ImageView wishlistIcon = new ImageView(new Image(
                getClass().getResourceAsStream("/com/images/logos/wishlist-1.png")));
        wishlistIcon.setFitWidth(20);
        wishlistIcon.setFitHeight(20);
        wishlistBtn.setGraphic(wishlistIcon);

        // Evento para añadir al carrito
        addToCartBtn.setOnAction(e -> {
            añadirAlCarrito(producto, cantidad[0]);
            mostrarAlerta("Producto añadido al carrito");
        });

        actionBox.getChildren().addAll(addToCartBtn, wishlistBtn);

        // Construir la tarjeta
        card.getChildren().addAll(imageView, nameLabel, descLabel, priceLabel, quantityBox, actionBox);

        return card;
    }

    // Mantén tus métodos existentes sin cambios
    private void añadirAlCarrito(Producto producto, int cantidad) {
        carrito.agregarItem(new ItemCarrito(producto, cantidad));
        System.out.println("Carrito: " + carrito.obtenerTodosItems().size() + " items");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}