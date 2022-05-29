package com.example.inventorymanagement.controllers;

import com.example.inventorymanagement.models.Inventory;
import com.example.inventorymanagement.models.Part;
import com.example.inventorymanagement.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddProdController class to implement the logic supporting the AddProductForm
 */

public class AddProdController implements Initializable {

    /**
     * static id field to keep track of Product ID
     */
    private static int id = 1;

    /**
     * ObservableList assocParts, which contains a list of parts to determine parts associated with a product
     */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();


    /**
     * searchBar to inject JavaFX TextField and hold user inputted product name / id to search
     */
    @FXML
    private TextField searchBar;


    /**
     * idText to inject JavaFX TextField and hold generated product ID if needed for manipulation
     */
    @FXML
    private TextField idText;

    /**
     * nameText to inject JavaFx TextField and hold user inputted name field
     */
    @FXML
    private TextField nameText;

    /**
     * invText to inject JavaFX TextField and hold user inputted inventory field
     */
    @FXML
    private TextField invText;

    /**
     * priceText to inject JavaFX TextField and hold user inputted price field
     */
    @FXML
    private TextField priceText;

    /**
     * maxText to inject JavaFX TextField and hold user inputted max field
     */
    @FXML
    private TextField maxText;

    /**
     * minText to inject JavaFX TextField and hold user inputted min field
     */
    @FXML
    private TextField minText;

    /**
     * topTable to inject JavaFX TableView and hold all available parts available for association
     */
    @FXML
    private TableView<Part> topTable;

    /**
     * assocTable to inject JavaFX TableView and hold all parts associated with a selected product
     */
    @FXML
    private TableView<Part> assocTable;

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
     * assocPartIdCol to inject JavaFX TableColumn and display the Part ID Column for all associated parts
     */
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    /**
     * assocPartNameCol to inject JavaFX TableColumn and display the Part Name Column for all associated parts
     */
    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    /**
     * assocInvCol to inject JavaFX TableColumn and display the Part Inventory Column for all associated parts
     */
    @FXML
    private TableColumn<Part, Integer> assocInvCol;

    /**
     * assocPriceCol to inject JavaFX TableColumn and display the Part Price Column for all associated parts
     */
    @FXML
    private TableColumn<Part, Double> assocPriceCol;


    /**
     * initializes the AddProdController and calls the
     * initializeTables(partIdCol, partNameCol, partInvCol, partPriceCol, topTable, assocPartIdCol, assocPartNameCol,
     * assocInvCol, assocPriceCol) method
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTables(partIdCol, partNameCol, partInvCol, partPriceCol, topTable, assocPartIdCol, assocPartNameCol,
                assocInvCol, assocPriceCol);
    }

    /**
     * static initializeTables() method is set to static for code reuse. It initializes both the topTable
     * and assocParts table with their respective columns. topTable is also initialized to display readily available parts
     *
     * @param partIdCol        available parts ID Column
     * @param partNameCol      available parts Name Column
     * @param partInvCol       available parts Inventory Column
     * @param partPriceCol     available parts Price Column
     * @param topTable         table to display available parts
     * @param assocPartIdCol   associated parts ID Column
     * @param assocPartNameCol associated parts Name Column
     * @param assocInvCol      associated parts Inventory Column
     * @param assocPriceCol    associated parts Price Column
     */
    static void initializeTables
    (TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvCol,
     TableColumn<Part, Double> partPriceCol, TableView<Part> topTable, TableColumn<Part, Integer> assocPartIdCol,
     TableColumn<Part, String> assocPartNameCol, TableColumn<Part, Integer> assocInvCol, TableColumn<Part, Double> assocPriceCol) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        topTable.setItems(Inventory.getAllParts());

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * addAssocPart() method retrieves the users selected part from the topTable and validates if the selected
     * part is not already in the assocTable AND if the selected part is not in the assocParts list. If valid,
     * the selected part is added as an associated part.
     */
    public void addAssocPart() {

        Part selectedPart = topTable.getSelectionModel().getSelectedItem();
        if (!(assocTable.getItems().contains(selectedPart)) && !(assocParts.contains(selectedPart))) {
            assocParts.add(selectedPart);
        }
        assocTable.setItems(assocParts);
    }

    /**
     * saveProduct() method accepts and validates user input. If user input is valid, the product is then saved
     * and the user is sent back to the main form
     *
     * @param event is passed to the returnToMain(event) method to return to the main form
     */
    public void saveProduct(ActionEvent event) {
        String name = nameText.getText();
        int inv = 0;
        double price = 0;
        int max = 0;
        int min = 0;

        /**
         * If/else statements to call the MainController.isNumeric() method to validate each data field. If valid,
         * assign data values to corresponding variables, else, return.
         */

        if (MainController.isNumeric(invText.getText())) inv = Integer.parseInt(invText.getText());
        else return;

        if (MainController.isDouble(priceText.getText())) price = Double.parseDouble(priceText.getText());
        else return;

        if (MainController.isNumeric(maxText.getText())) max = Integer.parseInt(maxText.getText());
        else return;

        if (!(inv >= min && inv <= max)) {
            MainController.displayAlert("invalidInventory");
            return;
        }

        /**
         * nested if-else statements to validate if minText is numeric, also if min less than or equal to maxText
         * else display corresponding alert(s)
         */
        if (MainController.isNumeric(minText.getText()))
            if (Integer.parseInt(minText.getText()) <= Integer.parseInt(maxText.getText()))
                min = Integer.parseInt(minText.getText());
            else {
                MainController.displayAlert("minMax");
                return;
            }
        else return;

        /**
         * Assign a new product and add the product to the inventory. Upon success, return to main form
         */
        Product product = new Product(id, name, price, inv, min, max);
        for (Part part : assocParts)
            product.addAssociatedPart(part);
        Inventory.addProduct(product);

        id++;

        MainController.returnToMain(event);
    }

    /**
     * searchParts() method calls the static searchParts(TextField searchBar, TableView<Part> topTable) method
     */
    public void searchParts() {
        searchParts(searchBar, topTable);
    }

    /**
     * @param searchBar the user inputted searchBar TextField to iterate through the Inventory and see if
     *                  the part exists. If the searchBar is empty, the table is populated with all parts
     * @param topTable  displays either all available parts or the parts that were found based on user input
     */
    public static void searchParts(TextField searchBar, TableView<Part> topTable) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if (searchBar.getText().isEmpty())
            topTable.setItems(Inventory.getAllParts());
        else
            for (Part part : Inventory.getAllParts())
                if (part.getName().contains(searchBar.getText()) || ((Integer) part.getId()).toString().equals(searchBar.getText()))
                    foundParts.add(part);

        topTable.setItems(foundParts);
    }

    /**
     * removeAssocPart() method removes the associated part selected by the user
     */
    public void removeAssocPart() {
        Part part = assocTable.getSelectionModel().getSelectedItem();
        assocParts.remove(part);
        assocTable.setItems(assocParts);
    }

    /**
     * cancelBtn(event) method accepts an event as a parameter, which then returns the user to the main form
     *
     * @param event is passed to the returnToMain(event) method
     */
    public void cancelBtn(ActionEvent event) {
        MainController.returnToMain(event);
    }

}
