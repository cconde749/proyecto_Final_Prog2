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
    @FXML private Label error_label;

    private Stage primaryStage;
    private UserManager userManager = UserManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Estilo y comportamiento del enlace de registro
        register_label.setStyle("-fx-text-fill: #2196F3; -fx-cursor: hand;");
        register_label.setOnMouseEntered(e -> register_label.setUnderline(true));
        register_label.setOnMouseExited(e -> register_label.setUnderline(false));

        register_label.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/tinapp/registro.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Registro");
                ((Node)(event.getSource())).getScene().getWindow().hide();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Carga de usuarios de prueba (puedes quitar esto en producción)
        userManager.registrar("admin@tinapp.com", "Admin", "admin123");
        userManager.registrar("user@tinapp.com", "Usuario", "user123");

        // Acción de inicio de sesión
        logIn_button.setOnAction(event -> {
            String email = textField_email.getText().trim();
            String password = textField_passwd.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                mostrarError("Por favor, complete todos los campos.");
                return;
            }

            if (userManager.autenticar(email, password)) {
                error_label.setVisible(false);
                cargarVistaPrincipal(email);
            } else {
                mostrarError("Credenciales incorrectas.");
            }
        });
    }

    private void mostrarError(String mensaje) {
        error_label.setText(mensaje);
        error_label.setVisible(true);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void cargarVistaPrincipal(String email) {
        try {
            // Cambiado a minúscula para coincidir con el nombre real del archivo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tinapp/catalog.fxml"));
            Parent root = loader.load();

            // Opcional: Pasar datos al controlador del catálogo
            CatalogoController controller = loader.getController();
            // Si necesitas pasar el email al catálogo:
            // controller.setUsuarioActual(email);

            Stage stage = (Stage) logIn_button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Catálogo Principal");
            stage.centerOnScreen(); // Centrar la ventana
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar el catálogo");
        }
    }
}