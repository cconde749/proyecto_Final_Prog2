package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MiCuentaController implements Initializable {
    @FXML private VBox historyBox;
    @FXML private Button exportBtn;
    @FXML private Label listaProductosEnc;
    @FXML private Label carritoEnc;
    @FXML private ImageView logoPrincipal;
    // Carrito de compras (usaremos una lista simple)
    private ListaSimpleCarrito carrito = new ListaSimpleCarrito();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarHistorial();
        exportBtn.setOnAction(e -> exportarHistorial());
        asignarEventosEncabezado();
    }

    private void asignarEventosEncabezado() {
        listaProductosEnc.setOnMouseClicked(this::irListaDeseos);
        carritoEnc.setOnMouseClicked(this::irAlCarrito2);
        logoPrincipal.setOnMouseClicked(this::irAlCatalogo);
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

    private void irAlCarrito2(MouseEvent e) {
        try {
            // Guardar el estado del carrito
            CarritoManager.getInstance().setCarrito(carrito);
            Stage stage = (Stage) carritoEnc.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Carrito.fxml"));
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

    private void cargarHistorial() {
        historyBox.getChildren().clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Purchase p : PurchaseHistoryManager.getInstance().getHistorial()) {
            Label fecha = new Label("Fecha: " + p.getFecha().format(fmt));
            fecha.setFont(Font.font(14));
            Label monto = new Label(String.format("Total: $%.2f", p.getTotal()));
            monto.setFont(Font.font(14));
            historyBox.getChildren().addAll(new Separator(), fecha, monto);
            for (ItemCarrito it : p.getItems()) {
                Label item = new Label("  • " + it.getProducto().getNombre() +
                        " x" + it.getCantidad() +
                        " = $" + String.format("%.2f", it.getSubtotal()));
                historyBox.getChildren().add(item);
            }
        }
    }

    private void exportarHistorial() {
        try {
            FileChooser fc = new FileChooser();
            fc.setInitialFileName("historial_compras.txt");
            File f = fc.showSaveDialog(exportBtn.getScene().getWindow());
            if (f == null) return;
            FileWriter w = new FileWriter(f);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Purchase p : PurchaseHistoryManager.getInstance().getHistorial()) {
                w.write("Fecha: " + p.getFecha().format(fmt) + "\n");
                w.write(String.format("Total: $%.2f\n", p.getTotal()));
                for (ItemCarrito it : p.getItems()) {
                    w.write("  - " + it.getProducto().getNombre() +
                            " x" + it.getCantidad() +
                            " = $" + String.format("%.2f", it.getSubtotal()) + "\n");
                }
                w.write("\n");
            }
            w.close();
            new Alert(Alert.AlertType.INFORMATION, "Historial exportado").showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al exportar").showAndWait();
        }
    }
}
