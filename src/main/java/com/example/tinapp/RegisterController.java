package com.example.tinapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
