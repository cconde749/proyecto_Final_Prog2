package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.function.Consumer;

public class ProductCardController {
    @FXML private AnchorPane cardRoot;
    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productDesc;
    @FXML private Label productPrice;
    @FXML private Label quantityLabel;
    @FXML private Button decreaseBtn;
    @FXML private Button increaseBtn;
    @FXML private Button addToCartBtn;
    @FXML private Button wishlistBtn;

    private int cantidad = 1;
    private Producto producto;
    private Consumer<Producto> wishlistHandler;
    private Consumer<CartItem> addToCartHandler;

    public void setData(Producto producto) {
        this.producto = producto;

        try {
            productImage.setImage(new Image(getClass().getResourceAsStream("/com/example/tinapp/images/products/" + producto.getImagenUrl())));
        } catch (Exception e) {
            productImage.setImage(new Image(getClass().getResourceAsStream("/com/example/tinapp/images/products/default.png")));
        }

        productName.setText(producto.getNombre());
        productDesc.setText(producto.getDescripcion());
        productPrice.setText(String.format("$%.2f", producto.getPrecio()));
        quantityLabel.setText(String.valueOf(cantidad));
    }

    @FXML
    private void aumentarCantidad() {
        cantidad++;
        quantityLabel.setText(String.valueOf(cantidad));
    }

    @FXML
    private void disminuirCantidad() {
        if (cantidad > 1) {
            cantidad--;
            quantityLabel.setText(String.valueOf(cantidad));
        }
    }

    @FXML
    private void añadirAlCarrito() {
        if (addToCartHandler != null) {
            addToCartHandler.accept(new CartItem(producto, cantidad));
        }
    }

    @FXML
    private void añadirAListaDeseos() {
        if (wishlistHandler != null) {
            wishlistHandler.accept(producto);
        }
    }

    // Setters para los handlers
    public void setAddToCartHandler(Consumer<CartItem> handler) {
        this.addToCartHandler = handler;
    }

    public void setWishlistHandler(Consumer<Producto> handler) {
        this.wishlistHandler = handler;
    }
}