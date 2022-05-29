module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.inventorymanagement to javafx.fxml;
    exports com.example.inventorymanagement;
    exports com.example.inventorymanagement.controllers;
    opens com.example.inventorymanagement.controllers to javafx.fxml;
    exports com.example.inventorymanagement.models;
    opens com.example.inventorymanagement.models to javafx.fxml;
}