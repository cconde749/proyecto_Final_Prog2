package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;

public class RegisterController implements Initializable {
    @FXML
    private TextField textField_name;
    @FXML
    private TextField textField_email;
    @FXML
    private TextField textField_passwd;
    @FXML
    private Button register_button;
    @FXML
    private Pane background_pane;
    @FXML private Pane register_pane;
    @FXML private Label login_label;
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Hacer el label clickeable
        login_label.setOnMouseClicked(event -> {
            try {
                // Cargar la nueva ventana
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/tinapp/login.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("LogIn");

                // Cerrar la ventana actual (opcional)
                ((Node)(event.getSource())).getScene().getWindow().hide();

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Estilo para que parezca un enlace
        login_label.setStyle("-fx-text-fill: #2196F3; -fx-cursor: hand;");
        login_label.setOnMouseEntered(e -> login_label.setUnderline(true));
        login_label.setOnMouseExited(e -> login_label.setUnderline(false));
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
