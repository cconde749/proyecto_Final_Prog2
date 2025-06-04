/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.tinapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML private TextField textField_email;
    @FXML private TextField textField_passwd;
    @FXML private Button logIn_button;
    @FXML private Pane background_pane;
    @FXML private Pane login_pane;
    @FXML private Label register_label;
    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Hacer el label clickeable
        register_label.setOnMouseClicked(event -> {
            try {
                // Cargar la nueva ventana
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/tinapp/register.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Registro");

                // Cerrar la ventana actual (opcional)
                ((Node)(event.getSource())).getScene().getWindow().hide();

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Estilo para que parezca un enlace
        register_label.setStyle("-fx-text-fill: #2196F3; -fx-cursor: hand;");
        register_label.setOnMouseEntered(e -> register_label.setUnderline(true));
        register_label.setOnMouseExited(e -> register_label.setUnderline(false));
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
}
