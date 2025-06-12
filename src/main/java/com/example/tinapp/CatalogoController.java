package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
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

    // Lista de productos
    private ListaDobleProductos listaProductos = new ListaDobleProductos();

    // Carrito de compras (usaremos una lista simple)
    private ListaSimpleCarrito carrito = new ListaSimpleCarrito();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProductosEjemplo();
        mostrarProductos();
    }

    private void cargarProductosEjemplo() {
        listaProductos.agregarProducto(new Producto("1", "Laptop Gamer",
                "Laptop de alto rendimiento", 1299.99, "laptop.png"));
        listaProductos.agregarProducto(new Producto("2", "Smartphone",
                "Último modelo con cámara 108MP", 799.99, "laptop.png"));
        listaProductos.agregarProducto(new Producto("3", "Diademas gamer",
                "Último modelo de diademas gamer", 799.99, "laptop.png"));
        listaProductos.agregarProducto(new Producto("4", "Mouse gamer",
                "Último modelo de mouse gamer", 799.99, "laptop.png"));
        listaProductos.agregarProducto(new Producto("5", "Mouse gamer",
                "Último modelo de mouse gamer", 799.99, "Keyboard.png"));
        listaProductos.agregarProducto(new Producto("6", "Mouse gamer",
                "Último modelo de mouse gamer", 799.99, "laptop.png"));
    }

    private void mostrarProductos() {
        productsGrid.getChildren().clear();
        // Configuración clave para reducir espacio
        productsGrid.setLayoutY(105);  // Posición exacta debajo del encabezado
        productsGrid.setVgap(20);    // Reducción de espacio vertical entre filas
        productsGrid.setHgap(20);    // Reducción de espacio horizontal entre columnas

        int column = 0;
        int row = 0;

        for (Producto producto : listaProductos.obtenerTodos()) {
            System.out.println(producto.getImagenUrl().toString());

            VBox card = crearTarjetaProducto(producto);
            productsGrid.add(card, column++, row);
            GridPane.setMargin(card, new Insets(20, 20, 20, 20)); // top, right, bottom, left

            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox crearTarjetaProducto(Producto producto) {
        VBox card = new VBox(8); // Espaciado interno reducido
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #FBACDB; -fx-background-radius: 10; -fx-padding: 15;");
        card.setEffect(new javafx.scene.effect.DropShadow(5, Color.gray(0.3)));

        // Tamaño fijo para la tarjeta
        card.setPrefSize(250, 250);

        // Imagen ajustada
        ImageView imageView = new ImageView();
        try {
            Image originalImage = new Image(
                    getClass().getResourceAsStream("/com/images/products/" + producto.getImagenUrl().trim()));
            System.out.println("/com/images/products/" + producto.getImagenUrl());

            imageView.setImage(originalImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
        } catch (Exception e) {
            imageView.setImage(new Image(getClass().getResourceAsStream(
                    "/com/images/products/diademas.png")));
        }
        // Configuración adicional del ImageView
        imageView.setFitWidth(179);
        imageView.setFitHeight(142);
        imageView.setPreserveRatio(true); // Desactivado porque ya lo controlamos al cargar
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.QUALITY);

        // Textos con fuentes ajustadas
        Label nameLabel = new Label(producto.getNombre());
        nameLabel.setFont(Font.font("System Bold", 14));
        nameLabel.setStyle("-fx-text-fill: #333; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(200);

        Label priceLabel = new Label(String.format("$%.2f", producto.getPrecio()));
        priceLabel.setFont(Font.font("System Bold", 16));
        priceLabel.setStyle("-fx-text-fill: #FE5F7D; -fx-font-weight: bold;");

        // Botones optimizados
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addToCartBtn = new Button("Carrito");
        addToCartBtn.setPrefSize(162, 28); // Tamaño fijo del botón
        addToCartBtn.setMaxSize(162, 28); // Evita que crezca
        addToCartBtn.setContentDisplay(ContentDisplay.LEFT); // Icono a la izquierda del texto
        addToCartBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        ImageView cartIcon = new ImageView(new Image(
                getClass().getResourceAsStream("/com/images/logos/shopping-cart.png")));
        cartIcon.setFitWidth(20);  // Tamaño del icono
        cartIcon.setFitHeight(20);
        cartIcon.setPreserveRatio(true);

        addToCartBtn.setGraphic(cartIcon);
        addToCartBtn.setStyle("-fx-background-color: #FE5F7D; -fx-text-fill: white; -fx-background-radius: 15;");
        addToCartBtn.setTooltip(new Tooltip("Añadir al carrito"));

        Button wishlistBtn = new Button();
        wishlistBtn.setPrefSize(28, 28); // Botón cuadrado para el icono
        wishlistBtn.setMaxSize(28, 28);

// Cargar y ajustar imagen de deseos
        ImageView wishlistIcon = new ImageView(new Image(
                getClass().getResourceAsStream("/com/images/logos/wishlist-1.png")));
        wishlistIcon.setFitWidth(20);  // Tamaño del icono
        wishlistIcon.setFitHeight(20);
        wishlistIcon.setPreserveRatio(true);

        wishlistBtn.setGraphic(wishlistIcon);
        wishlistBtn.setStyle("-fx-background-color: transparent; -fx-background-radius: 15;");
        wishlistBtn.setTooltip(new Tooltip("Añadir a deseos"));

        buttonBox.getChildren().addAll(addToCartBtn, wishlistBtn);

        // Eventos
        addToCartBtn.setOnAction(e -> {
            añadirAlCarrito(producto, 1);
            mostrarAlerta(producto.getNombre() + " añadido al carrito");
        });

        // Construir tarjeta
        card.getChildren().addAll(imageView, nameLabel, priceLabel, buttonBox);

        return card;
    }

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