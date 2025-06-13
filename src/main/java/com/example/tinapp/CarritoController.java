package com.example.tinapp;

import com.example.tinapp.ItemCarrito;
import com.example.tinapp.ListaSimpleCarrito;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarritoController implements Initializable {
    @FXML
    private ListView<ItemCarrito> carritoListView;
    @FXML private Label totalLabel;

    private ListaSimpleCarrito carrito;
    private CarritoManager carritoManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtener el carrito guardado
        carrito = carritoManager.getInstance().getCarrito();

        // Configurar el ListView
        carritoListView.setItems(carrito.obtenerTodosItems());
        carritoListView.setCellFactory(lv -> new ListCell<ItemCarrito>() {
            @Override
            protected void updateItem(ItemCarrito item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Crear una celda personalizada para cada item
                    HBox cell = new HBox(10);
                    // ... similar a lo que hicimos en el resumen
                    setGraphic(cell);
                }
            }
        });

        // Actualizar el total
        actualizarTotal();
    }

    private void actualizarTotal() {
        totalLabel.setText(String.format("Total: $%.2f", carrito.calcularTotal()));
    }

    @FXML
    private void volverAlCatalogo() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Catalogo.fxml"));
            Scene scene = totalLabel.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}