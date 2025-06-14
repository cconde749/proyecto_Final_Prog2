package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogoController implements Initializable {
    @FXML private GridPane productsGrid;
    @FXML private GridPane shopResumeGrid;
    @FXML private Button goCart;

    // Lista de productos
    private ListaDobleProductos listaProductos = new ListaDobleProductos();

    // Carrito de compras (usaremos una lista simple)
    private ListaSimpleCarrito carrito = new ListaSimpleCarrito();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProductosEjemplo();
        mostrarProductos();
        // Configurar el botón del carrito
        goCart.setOnAction(e -> irAlCarrito());
    }

    @FXML
    private void irAlCarrito() {
        try {
            // Guardar el estado del carrito
            CarritoManager.getInstance().setCarrito(carrito);

            Stage currentStage = (Stage) goCart.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Carrito.fxml"));
            Parent root = loader.load();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar la vista del carrito");
        }
    }

    private void guardarEstadoCarrito() {
        // Aquí puedes guardar el estado del carrito en una clase singleton o en el modelo
        // Por ejemplo:
        CarritoManager.getInstance().setCarrito(carrito);
    }

    private void cargarProductosEjemplo() {
        listaProductos.agregarProducto(new Producto("1", "Laptop Gamer",
                "Laptop de alto rendimiento", 1299.99, "laptop.png"));

        listaProductos.agregarProducto(new Producto("2", "Smartphone",
                "Celular IPhone 16 pro max", 799.99, "IPhone.png"));

        listaProductos.agregarProducto(new Producto("3", "Diademas_gamer",
                "Diademas gamer", 799.99, "diademas.png"));

        listaProductos.agregarProducto(new Producto("4", "Mouse gamer",
                "Mouse gamer", 799.99, "mouse.png"));

        listaProductos.agregarProducto(new Producto("5", "Mando_pc",
                "Mando de videojuegos para pc", 799.99, "gamepad.png"));

        listaProductos.agregarProducto(new Producto("6", "Teclado",
                "Teclado de oficina", 799.99, "Keyboard.png"));
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
        VBox card = new VBox(1); // Espaciado interno reducido
        card.setAlignment(Pos.BOTTOM_CENTER);
        card.setStyle("-fx-background-color: #FBACDB;");
        card.setEffect(new DropShadow(5, Color.gray(0.3)));

        // Tamaño fijo para la tarjeta
        card.setMinSize(250, 250);
        card.setMaxSize(250, 250);

        // Imagen ajustada
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setSmooth(true);
        try {
            Image originalImage = new Image(
                    getClass().getResourceAsStream("/com/images/products/" + producto.getImagenUrl().trim()));
            System.out.println("/com/images/products/" + producto.getImagenUrl());


            imageView.setImage(originalImage);

        } catch (Exception e) {
            imageView.setImage(new Image(getClass().getResourceAsStream(
                    "/com/images/products/diademas.png")));
        }

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
        addToCartBtn.setOnAction(e -> {
            // 1) Creas o reutilizas tu objeto ItemCarrito
            ItemCarrito nuevo = new ItemCarrito(producto, 1);
            // 2) Lo agregas a tu lista interna
            carrito.agregarItem(nuevo);  // método que inserta en tu ListaSimpleCarrito
            // 3) Refrescas el grid de resumen
            actualizarResumenCarrito();
        });
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

    private void actualizarResumenCarrito() {
        shopResumeGrid.getChildren().clear();
        shopResumeGrid.setMinWidth(250);
        shopResumeGrid.setMaxWidth(250);

        int row = 0;
        for (ItemCarrito item : carrito.obtenerTodosItems()) {
            // Crear contenedor para cada item del carrito
            HBox itemBox = new HBox(7);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setStyle("-fx-background-color: #FBACDB; -fx-padding: 5; -fx-background-radius: 5;");

            // Imagen del producto
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(getClass().getResourceAsStream(
                        "/com/images/products/" + item.getProducto().getImagenUrl().trim()));
                imageView.setImage(image);
            } catch (Exception e) {
                imageView.setImage(new Image(getClass().getResourceAsStream(
                        "/com/images/products/diademas.png")));
            }
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);

            // Control de cantidad con Spinner
            Spinner<Integer> spinner = new Spinner<>(1, 10, item.getCantidad());
            spinner.setEditable(true);
            spinner.setMaxSize(50,30);
            spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                item.setCantidad(newValue);
                actualizarResumenCarrito(); // Actualizar para reflejar cambios
            });

            // Botón para eliminar
            Button removeBtn = new Button("Eliminar");
            removeBtn.setOnAction(ev -> {
                carrito.eliminarItem(item.toString());    // tu método para quitar de la lista enlazada
                actualizarResumenCarrito();    // refresca la vista
            });

            // Label para mostrar subtotal
            Label subtotalLabel = new Label(String.format("$%.2f", item.getSubtotal()));
            subtotalLabel.setStyle("-fx-font-weight: bold;");

            itemBox.getChildren().addAll(imageView, spinner, removeBtn, subtotalLabel);
            shopResumeGrid.add(itemBox, 0, row++);
        }

        // Agregar fila con el total
        Label totalLabel = new Label("Total: $" + String.format("%.2f", carrito.calcularTotal()));
        totalLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        shopResumeGrid.add(totalLabel, 0, row);
    }

    private void añadirAlCarrito(Producto producto, int cantidad) {

            carrito.agregarItem(new ItemCarrito(producto, cantidad));
            actualizarResumenCarrito();
            mostrarAlerta(producto.getNombre() + " añadido al carrito");

    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}