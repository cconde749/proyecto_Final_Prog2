package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CarritoController implements Initializable {
    @FXML private GridPane carritoGrid;
    @FXML private Label subtotalLabel;
    @FXML private Label ivaLabel;
    @FXML private Label totalLabel;
    @FXML private Button pagarBtn;
    @FXML private Label listaProductosEnc;
    @FXML private Label carritoEnc;
    @FXML private Label miCuentaEnc;
    @FXML private ImageView logoPrincipal;

    private ListaSimpleCarrito carrito;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carrito = CarritoManager.getInstance().getCarrito();
        cargarCarritoVisual();
        calcularTotales();

        // En CarritoController.initialize(...)
        pagarBtn.setOnAction(e -> {
            // 1) Alerta de éxito
            Alert ok = new Alert(Alert.AlertType.INFORMATION, "¡Compra exitosa!", ButtonType.OK);
            ok.showAndWait();

            // 2) Guardar en historial
            List<ItemCarrito> items = new ArrayList<>(carrito.obtenerTodosItems());
            double total = carrito.calcularTotal();
            Purchase p = new Purchase(LocalDateTime.now(), total, items);
            PurchaseHistoryManager.getInstance().agregarCompra(p);

            // 3) Resetear carrito
            CarritoManager.getInstance().setCarrito(new ListaSimpleCarrito());

            // 4) Volver al catálogo
            try {
                Stage stage = (Stage) pagarBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Catalogo.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) { ex.printStackTrace(); }
        });

        asignarEventosEncabezado();
    }

    private void asignarEventosEncabezado() {
        logoPrincipal.setOnMouseClicked(this::irAlCatalogo);
        listaProductosEnc.setOnMouseClicked(this::irListaDeseos);
        miCuentaEnc.setOnMouseClicked(this::irMiCuenta);
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

    private void irListaDeseos(MouseEvent e) {
        try {
            Stage stage = (Stage) listaProductosEnc.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ListaDeseos.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irMiCuenta(MouseEvent e) {
        try {
            Stage stage = (Stage) miCuentaEnc.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MiCuenta.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
