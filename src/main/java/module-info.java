module com.example.tinapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tinapp to javafx.fxml;
    exports com.example.tinapp;
}