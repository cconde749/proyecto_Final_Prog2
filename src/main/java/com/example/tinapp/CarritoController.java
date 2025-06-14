package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CarritoController implements Initializable {
    @FXML private GridPane carritoGrid;
    @FXML private Label subtotalLabel;
    @FXML private Label ivaLabel;
    @FXML private Label totalLabel;
    @FXML private Button pagarBtn;

    private ListaSimpleCarrito carrito;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carrito = CarritoManager.getInstance().getCarrito();
        cargarCarritoVisual();
        calcularTotales();

        pagarBtn.setOnAction(e -> {
            // Redirigir a la pasarela de pago
            System.out.println("Redirigiendo a la pasarela de pago...");
            // AQUI IMPLEMENTAS LA REDIRECCIÓN
        });
    }

    private void cargarCarritoVisual() {
        carritoGrid.getChildren().clear();
        int row = 0;

        for (ItemCarrito item : carrito.obtenerTodosItems()) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setStyle("-fx-background-color: #FBACDB; -fx-padding: 5; -fx-background-radius: 5;");

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

            Label nombre = new Label(item.getProducto().getNombre());
            Label cantidad = new Label("x" + item.getCantidad());
            Label subtotal = new Label(String.format("$%.2f", item.getSubtotal()));

            itemBox.getChildren().addAll(imageView, nombre, cantidad, subtotal);
            carritoGrid.add(itemBox, 0, row++);
        }
    }

    private void calcularTotales() {
        double subtotal = carrito.calcularTotal();
        double iva = 0.0; // IVA fijo por ahora
        double total = subtotal + iva;

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        ivaLabel.setText(String.format("$%.2f", iva));
        totalLabel.setText(String.format("$%.2f", total));
    }
}
