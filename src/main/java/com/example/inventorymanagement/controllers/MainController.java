package com.example.inventorymanagement.controllers;

import com.example.inventorymanagement.models.Inventory;
import com.example.inventorymanagement.models.Part;
import com.example.inventorymanagement.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainController class to implement the logic supporting the MainForm, as well as implementing
 * helper functions for other controllers
 */

public class MainController implements Initializable {

    /**
     * static modifyPart to retain the part being modified and passed to the ModifyPartController
     */
    private static Part modifyPart;

    /**
     * static modifyProduct to retain the product being modified and passed to the ModifyProductController
     */
    private static Product modifyProduct;


    /**
     * partsSearchBar to inject JavaFX TextField and hold the user criteria to search for a part
     */
    @FXML
    private TextField partsSearchBar;

    /**
     * partsTable to inject JavaFX TableView and hold all available parts
     */
    @FXML
    private TableView<Part> partsTable;

    /**
     * partIdCol to inject JavaFX TableColumn and display the Part ID Column for all available parts
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * partNameCol to inject JavaFX TableColumn and display the Part Name Column for all available parts
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * partInvCol to inject JavaFX TableColumn and display the Part Inventory Column for all available parts
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /**
     * partPriceCol to inject JavaFX TableColumn and display the Part Price Column for all available parts
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * prodSearchBar to inject JavaFX TextField and hold the user criteria to search for a product
     */
    @FXML
    private TextField prodSearchBar;

    /**
     * productTable to inject JavaFX TableView and display all available products
     */
    @FXML
    private TableView<Product> productTable;

    /**
     * prodIdCol to inject JavaFX TableColumn and display the Product ID Column
     */
    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    /**
     * prodNameCol to inject JavaFX TableColumn and display the Product Name Column
     */
    @FXML
    private TableColumn<Product, String> prodNameCol;

    /**
     * prodInvCol to inject JavaFX TableColumn and display the Product Inventory Column
     */
    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    /**
     * prodPriceCol to inject JavaFX TableColumn and display the Product Price Column
     */
    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    /**
     * initializes the partsTable and productTable with available parts and products, respectively.
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());
    }

    /**
     * addPart(event) method to redirect the user to the AddPartForm
     *
     * @param event used to get the source and hide MainForm window
     */
    public void addPart(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddPartForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * modifyPart(event) method to retrieve the user selected part to modify. If modifyPart is null, display alert
     * if successful, redirect user to the ModifyPartForm, else, return
     *
     * @param event
     */
    @FXML
    public void modifyPart(ActionEvent event) {
        modifyPart = partsTable.getSelectionModel().getSelectedItem();
        if (modifyPart == null) {
            displayAlert("selectPart");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyPartForm.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * getModifyPart() method retrieves the part to be modified
     *
     * @return retrieves modifyPart
     */
    @FXML
    public Part getModifyPart() {
        return modifyPart;
    }


    /**
     * deletePart() method retrieves the user selected part to be deleted. If deletion is unsuccessful, alert the user
     */
    public void deletePart() {
        modifyPart = partsTable.getSelectionModel().getSelectedItem();
        if (!(Inventory.deletePart(modifyPart)))
            displayAlert("partDel");
    }


    /**
     * searchParts() method which retrieves all available parts with a matching ID or a substring of a part name.
     * if part is not found, alert user. If no criteria is inputted for a search, all available parts will be displayed
     */
    public void searchParts() {

        ObservableList<Part> allParts;
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if (partsSearchBar.getText().isEmpty())
            partsTable.setItems(Inventory.getAllParts());
        else {
            allParts = Inventory.getAllParts();
            for (Part part : allParts)
                if (part.getName().contains(partsSearchBar.getText()) || ((Integer) part.getId()).toString().equals(partsSearchBar.getText()))
                    foundParts.add(part);

            if (foundParts.isEmpty())
                displayAlert("partnf");

            partsTable.setItems(foundParts);
        }
    }


    /**
     * addProduct() method to redirect the user to the AddProductForm
     */
    public void addProduct(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddProductForm.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * modifyProduct(event) method to retrieve the user selected product to modify. If modifyProduct is null, display alert
     * if successful, redirect user to the ModifyPartForm, else, return
     *
     * @param event used to get the source and hide the window
     */
    @FXML
    public void modifyProduct(ActionEvent event) {
        modifyProduct = productTable.getSelectionModel().getSelectedItem();
        if (modifyProduct == null) {
            displayAlert("selectProduct");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyProductForm.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * searchProducts() method which retrieves all available products with a matching ID or a substring of a product name.
     * if product is not found, alert user. If no criteria is inputted for a search, all available products will be displayed
     */
    public void searchProducts() {
        ObservableList<Product> allProducts;
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if (prodSearchBar.getText().isEmpty())
            productTable.setItems(Inventory.getAllProducts());
        else {
            allProducts = Inventory.getAllProducts();
            for (Product product : allProducts)
                if (product.getName().contains(prodSearchBar.getText()) || ((Integer) product.getId()).toString().equals(prodSearchBar.getText()))
                    foundProducts.add(product);

            if (foundProducts.isEmpty())
                displayAlert("prodnf");
            productTable.setItems(foundProducts);
        }

    }


    /**
     * getModifyProduct() method retrieves the product to be modified
     *
     * @return retrieves modifyProduct
     */
    @FXML
    public Product getModifyProduct() {
        return modifyProduct;
    }


    /**
     * deletePart() method retrieves the user selected product to be deleted. If no product is selected, alert the user
     * If deletion is unsuccessful, alert the user, and if the product is associated with any parts, reject deletion
     * and alert the user
     */
    public void deleteProduct() {
        modifyProduct = productTable.getSelectionModel().getSelectedItem();

        if (modifyProduct == null) {
            displayAlert("prodDel");
            return;
        }

        if (modifyProduct.getAssociatedParts().isEmpty()) {
            if (!(Inventory.deleteProduct(modifyProduct)))
                displayAlert("prodDel");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please delete ALL associated parts with the product!");
            alert.showAndWait();

        }
    }

    /**
     * exitPane() method exits the application upon selection of the 'Exit' button
     */
    public void exitPane() {
        System.exit(0);
    }

    /**
     * static returnToMain(event) method used as a helper method to return to MainForm from other controllers
     *
     * @param event used to get the source and hide the current window
     */
    public static void returnToMain(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/fxml/MainForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * displayAlert(alertMessage) method accepts a String corresponding as an error code in the switch statement
     *
     * @param alertMessage accepts a String, which is used in a switch() statement to determine the appropriate user alert
     */
    public static void displayAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertMessage) {
            case "partnf": {
                alert.setTitle("Part not found");
                alert.setContentText("Unable to find part");
                alert.showAndWait();
                break;
            }
            case "prodnf": {
                alert.setTitle("Product not found");
                alert.setContentText("Unable to find product");
                alert.showAndWait();
                break;
            }
            case "partDel": {
                alert.setTitle("Part Delete Unsuccessful");
                alert.setContentText("Unable to delete specified part");
                alert.showAndWait();
                break;
            }
            case "prodDel": {
                alert.setTitle("Product Delete Unsuccessful");
                alert.setContentText("Unable to delete specified product");
                alert.showAndWait();
                break;
            }
            case "minMax": {
                alert.setTitle("Min Max Error");
                alert.setContentText("Min value cannot be greater than the max value!");
                alert.showAndWait();
                break;
            }
            case "dataType": {
                alert.setTitle("Data Type Error");
                alert.setContentText("Incorrect data value!");
                alert.showAndWait();
                break;
            }
            case "selectProduct": {
                alert.setTitle("Select a Product");
                alert.setContentText("Please select a product to modify!");
                alert.showAndWait();
                break;
            }
            case "selectPart": {
                alert.setTitle("Select a Part");
                alert.setContentText("Please select a part to modify!");
                alert.showAndWait();
                break;
            }
            case "emptyField": {
                alert.setTitle("Empty Field(s)");
                alert.setContentText("Please fill missing field(s)");
                alert.showAndWait();
                break;
            }
            case "invalidInventory": {
                alert.setTitle("Invalid Inventory Level");
                alert.setContentText("Inventory level must be within the min and max values");
                alert.showAndWait();
                break;
            }
        }
    }


    /**
     * isNumeric(checkString) static method is used to validate user input for numeric fields. It is static to serve
     * as a helper method for other controllers to reduce redundancy.
     *
     * @param checkString String variable to be parsed as an Integer for comparison
     * @return return statement to return whether the corresponding argument is numeric
     */
    public static boolean isNumeric(String checkString) {
        int intValue;

        try {
            intValue = Integer.parseInt(checkString);
            return true;
        } catch (NumberFormatException e) {
            MainController.displayAlert("dataType");
        }
        return false;
    }


    /**
     * isDouble(checkString) static method is used to validate user input for floating point fields. It is static to serve
     * as a helper method for other controllers to reduce redundancy.
     *
     * @param checkString String variable to be parsed as a Double for comparison
     * @return return statement to return whether the corresponding argument is a floating point number.
     */
    public static boolean isDouble(String checkString) {
        double doubleValue;

        try {
            doubleValue = Double.parseDouble(checkString);
            return true;
        } catch (NumberFormatException e) {
            MainController.displayAlert("dataType");
        }
        return false;
    }

}


