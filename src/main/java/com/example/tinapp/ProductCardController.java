package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductCardController {

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productDesc;

    @FXML
    private Label productPrice;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button increaseBtn;

    @FXML
    private Button decreaseBtn;

    @FXML
    private Button addToCartBtn;

    @FXML
    private Button wishlistBtn;

    private int cantidad = 1;

    public void setData(Producto producto) {
        productName.setText(producto.getNombre());
        productDesc.setText(producto.getDescripcion());
        productPrice.setText("$" + producto.getPrecio());

        try {
            Image image = new Image(getClass().getResourceAsStream(producto.getImagenUrl()));
            productImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Error cargando imagen: " + producto.getImagenUrl());
        }

        quantityLabel.setText(String.valueOf(cantidad));

        increaseBtn.setOnAction(e -> {
            cantidad++;
            quantityLabel.setText(String.valueOf(cantidad));
        });

        decreaseBtn.setOnAction(e -> {
            if (cantidad > 1) {
                cantidad--;
                quantityLabel.setText(String.valueOf(cantidad));
            }
        });

        addToCartBtn.setOnAction(e -> {
            System.out.println("Añadido al carrito: " + producto.getNombre() + " x" + cantidad);
            // Aquí puedes añadir el producto al carrito con la cantidad
        });

        wishlistBtn.setOnAction(e -> {
            System.out.println("Agregado a wishlist: " + producto.getNombre());
        });
    }
}
