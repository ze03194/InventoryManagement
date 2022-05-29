package com.example.inventorymanagement.controllers;

import com.example.inventorymanagement.models.Inventory;
import com.example.inventorymanagement.models.Part;
import com.example.inventorymanagement.models.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyProductController class to implement the logic supporting the ModifyProductForm
 */
public class ModifyProductController implements Initializable {

    /**
     * product variable to hold the user selected product for modification
     */
    private Product product;

    /**
     * ObservableList assocParts, which contains a list of parts to determine parts associated with a product
     */
    private ObservableList<Part> assocParts;

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
     * initialize ModifyProductController. Retrieve user selected product for modification and display the properties of
     * the product as well as all the products associated parts.
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainController controller = new MainController();
        product = controller.getModifyProduct();
        topTable.setItems(Inventory.getAllParts());
        if (!(product.getAssociatedParts().isEmpty()))
            assocTable.setItems(product.getAssociatedParts());

        assocParts = product.getAssociatedParts();

        initializeItem(idText, product.getId(), nameText, product.getName(), invText, product.getStock(), priceText,
                product.getPrice(), maxText, product.getMax(), minText, product.getMin());

        AddProdController.initializeTables(partIdCol, partNameCol, partInvCol, partPriceCol, topTable, assocPartIdCol,
                assocPartNameCol, assocInvCol, assocPriceCol);
    }

    /**
     * static initializeItem() method used as a helper method betwen ModifyProductController and ModifyPartController
     * to initialize modified products and parts respectively
     *
     * @param idText    accepts the user inputted id value
     * @param id
     * @param nameText  accepts the user inputted name value
     * @param name
     * @param invText   accepts the user inputted inventory value
     * @param stock
     * @param priceText accepts the user inputted price value
     * @param price
     * @param maxText   accepts the user inputted max value
     * @param max
     * @param minText   accepts the user inputted min value
     * @param min
     */
    static void initializeItem(
            TextField idText, Integer id, TextField nameText, String name, TextField invText, Integer stock,
            TextField priceText, Double price, TextField maxText, Integer max, TextField minText, Integer min) {
        idText.setText(id.toString());
        nameText.setText(name);
        invText.setText(stock.toString());
        priceText.setText(price.toString());
        maxText.setText(max.toString());
        minText.setText(min.toString());
    }

    /**
     * addAssocPart() method retrieves the users selected part from the topTable and validates if the selected
     * part is not already in the assocTable AND if the selected part is not in the assocParts list. If valid,
     * the selected part is added as an associated part.
     */
    public void addAssocPart() {
        Part selectedPart = topTable.getSelectionModel().getSelectedItem();
        if (!(assocParts.contains(selectedPart)))
            assocParts.add(selectedPart);
        assocTable.setItems(assocParts);
    }

    /**
     * saveProduct() method accepts and validates user input. If user input is valid, the product is then saved
     * and the user is sent back to the main form
     *
     * @param event is passed to the returnToMain(event) method to return to the main form
     */
    public void saveProduct(ActionEvent event) {
        Product modifiedProduct = Inventory.lookupProduct(product.getId());
        int index;
        int inv, min, max;

        if (modifiedProduct != null) {
            index = Inventory.getAllProducts().indexOf(product);
            modifiedProduct.setName(nameText.getText());

            /**
             * If/else statements to call the MainController.isNumeric() method to validate each data field. If valid,
             * assign data values to corresponding variables, else, return.
             */
            if (MainController.isNumeric(invText.getText())) {
                modifiedProduct.setStock(Integer.parseInt(invText.getText()));
                inv = Integer.parseInt(invText.getText());
            } else
                return;

            if (MainController.isDouble(priceText.getText()))
                modifiedProduct.setPrice(Double.parseDouble(priceText.getText()));
            else
                return;

            /**
             * nested if-else statements to validate if minText is numeric, also if min less than or equal to maxText
             * else display corresponding alert(s)
             */
            if (MainController.isNumeric(minText.getText())) {
                if (Integer.parseInt(minText.getText()) <= Integer.parseInt(maxText.getText())) {
                    modifiedProduct.setMin(Integer.parseInt(minText.getText()));
                    min = Integer.parseInt(minText.getText());
                } else {
                    MainController.displayAlert("minMax");
                    return;
                }
            } else
                return;

            if (MainController.isNumeric(maxText.getText())) {
                modifiedProduct.setMax(Integer.parseInt(maxText.getText()));
                max = Integer.parseInt(maxText.getText());
            } else
                return;

            for (Part part : assocParts)
                if (!(modifiedProduct.getAssociatedParts().contains(part)))
                    modifiedProduct.addAssociatedPart(part);


            if (!(inv >= min && inv <= max)) {
                MainController.displayAlert("invalidInventory");
                return;
            }

            Inventory.updateProduct(index, modifiedProduct);
        }

        MainController.returnToMain(event);
    }

    /**
     * searchParts() method calls the static searchParts(TextField searchBar, TableView<Part> topTable) method
     */
    public void searchParts() {
        AddProdController.searchParts(searchBar, topTable);
    }

    /**
     * removeAssocPart() method removes the associated part selected by the user
     */
    public void removeAssocPart() {
        Part part = assocTable.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(part);
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
