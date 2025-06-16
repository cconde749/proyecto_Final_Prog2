package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class ListaDeseosController implements Initializable {

    @FXML private GridPane wishlistGrid;
    @FXML private ImageView logoPrincipal;
    @FXML private ImageView carritoBtn;
    @FXML private ImageView deseosBtn;
    @FXML private ImageView MiCuenta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaDeseos();
        asignarEventosEncabezado();
    }

    private void cargarListaDeseos() {
        Stack<Producto> deseos = ListaDeseosManager.getInstance().obtenerListaDeseos();
        wishlistGrid.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Producto producto : deseos) {
            VBox card = crearTarjetaProducto(producto);
            wishlistGrid.add(card, column++, row);
            GridPane.setMargin(card, new Insets(10));
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox crearTarjetaProducto(Producto producto) {
        VBox card = new VBox(1);
        card.setAlignment(Pos.BOTTOM_CENTER);
        card.setStyle("-fx-background-color: #FBACDB;");
        card.setEffect(new DropShadow(5, Color.gray(0.3)));
        card.setMinSize(250, 250);
        card.setMaxSize(250, 250);

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setSmooth(true);
        try {
            Image img = new Image(getClass().getResourceAsStream("/com/images/products/" + producto.getImagenUrl().trim()));
            imageView.setImage(img);
        } catch (Exception e) {
            imageView.setImage(new Image(getClass().getResourceAsStream("/com/images/products/diademas.png")));
        }

        Label nameLabel = new Label(producto.getNombre());
        nameLabel.setFont(Font.font("System Bold", 14));
        nameLabel.setStyle("-fx-text-fill: #333; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(200);

        Label priceLabel = new Label(String.format("$%.2f", producto.getPrecio()));
        priceLabel.setFont(Font.font("System Bold", 16));
        priceLabel.setStyle("-fx-text-fill: #FE5F7D; -fx-font-weight: bold;");

        // Después de priceLabel…
        Button addToCart = new Button("Carrito");
        addToCart.setOnAction(ev -> {
            ItemCarrito ic = new ItemCarrito(producto, 1);
            CarritoManager.getInstance().getCarrito().agregarItem(ic);
            mostrarAlerta(producto.getNombre() + " añadido al carrito");
        });
        addToCart.setStyle("-fx-background-color:#FE5F7D; -fx-text-fill:white; -fx-background-radius:15;");
        //card.getChildren().add(addToCart);


        card.getChildren().addAll(imageView, nameLabel, priceLabel, addToCart);
        return card;
    }

    private void asignarEventosEncabezado() {
        logoPrincipal.setOnMouseClicked(this::irAlCatalogo);
        carritoBtn.setOnMouseClicked(this::irAlCarrito);
        deseosBtn.setOnMouseClicked(this::irListaDeseos);
        MiCuenta.setOnMouseClicked(this::irMiCuenta);
    }

    private void irMiCuenta(MouseEvent e) {
        try {
            Stage stage = (Stage) MiCuenta.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MiCuenta.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irAlCatalogo(MouseEvent e) {
        try {
            Stage stage = (Stage) logoPrincipal.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Catalogo.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irAlCarrito(MouseEvent e) {
        try {
            Stage stage = (Stage) carritoBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Carrito.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irListaDeseos(MouseEvent e) {
        // Ya está en la lista de deseos
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
