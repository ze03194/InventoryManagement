package com.example.inventorymanagement.controllers;

import com.example.inventorymanagement.models.InHouse;
import com.example.inventorymanagement.models.Inventory;
import com.example.inventorymanagement.models.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddPartController class to implement the logic supporting the AddPartForm
 */

public class AddPartController implements Initializable {

    /**
     * id static variable to keep track of part id
     */
    private static int id = 1;

    /**
     * inHouseRadio to inject JAVAFX RadioButton and hold a radio selection
     */
    @FXML
    private RadioButton inHouseRadio;

    /**
     * outSourcedRadio to inject JAVAFX RadioButton and hold a radio selection
     */
    @FXML
    private RadioButton outSourcedRadio;

    /**
     * idText to inject JavaFX TextField and hold user inputted id field
     */
    @FXML
    private TextField idText;


    /**
     * nameText to inject JavaFX TextField and hold user inputted name field
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
     * minText to inject JavaFX TextField and hold user inputted min field
     */
    @FXML
    private TextField minText;


    /**
     * maxText to inject JavaFX Textfield and hold user inputted max field
     */
    @FXML
    private TextField maxText;


    /**
     * machineCompanyText to inject JavaFX TextField and hold user inputted machine id / company name
     */
    @FXML
    private TextField machineCompanyText;


    /**
     * machineCompanyLabel to inject JavaFX Label and hold machine id / company name label for manipulation
     */
    @FXML
    private Label machineCompanyLabel;


    /**
     * Initializes the AddPartController, setting the in-house radio selector as true
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadio.setSelected(true);
    }

    /**
     * inHouseRadioSelect() is an onAction method that executes when the inHouseRadio button is selected
     * Upon selection, outSourcedRadio is set to false and the machineCompanyLabel is set to "Machine ID"
     */
    public void inHouseRadioSelector() {
        inHouseRadio.setSelected(true);
        outSourcedRadio.setSelected(false);
        machineCompanyLabel.setText("Machine ID");

    }

    /**
     * outSourcedRadioSelect() is an onAction method that executes when the outSourcedRadio button is selected.
     * Upon selection, inHouseRadio is set to false and the machineCompanyLabel is set to "Company Label"
     */
    public void outSourceRadioSelector() {
        outSourcedRadio.setSelected(true);
        inHouseRadio.setSelected(false);
        machineCompanyLabel.setText("Company Name");

    }

    /**
     * savePart() method accepts and validates user input. If user input is valid, the part is then saved
     * and the user is sent back to the main form
     *
     * @param event is passed to the returnToMain(event) method to return to the main form
     */
    public void savePart(ActionEvent event) {

        String name = nameText.getText();

        int inv = 0;
        double price = 0;
        int max = 0;
        int min = 0;

        /**
         * If/else statements to call the MainController.isNumeric() method to validate each data field
         */
        if (MainController.isNumeric(invText.getText()))
            inv = Integer.parseInt(invText.getText());
        else
            return;


        if (MainController.isDouble(priceText.getText()))
            price = Double.parseDouble(priceText.getText());
        else
            return;


        if (MainController.isNumeric(maxText.getText()))
            max = Integer.parseInt(maxText.getText());
        else
            return;


        if (MainController.isNumeric(minText.getText())) {
            if (Integer.parseInt(minText.getText()) <= Integer.parseInt(maxText.getText()))
                min = Integer.parseInt(minText.getText());
            else {
                MainController.displayAlert("minMax");
                return;
            }
        } else
            return;


        if (!(inv >= min && inv <= max)) {
            MainController.displayAlert("invalidInventory");
            return;
        }

        String companyName;
        int machineID = 0;
        Outsourced outsourced;
        InHouse inHouse;

        if (name.isEmpty())
            MainController.displayAlert("emptyField");


        /**
         * if inHouseRadio is selected, assign the machineID and create a new inHouse part
         * else-if outSourcedRadio is selected, assign the company name and create a new outSourced part
         */
        if (inHouseRadio.isSelected()) {
            try {
                if (MainController.isNumeric(machineCompanyText.getText()))
                    machineID = Integer.parseInt(machineCompanyText.getText());
                else {
                    return;
                }
                inHouse = new InHouse(id, name, price, inv, min, max, machineID);
                id++;
                Inventory.addPart(inHouse);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        } else if (outSourcedRadio.isSelected()) {
            try {
                companyName = machineCompanyText.getText();
                outsourced = new Outsourced(id, name, price, inv, min, max, companyName);
                id++;
                Inventory.addPart(outsourced);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * call MainController returnToMain(event) method to return to the main form
         */
        MainController.returnToMain(event);
    }

    /**
     * all MainController returnToMain(event) method to return to the main form
     *
     * @param event is passed to the returnToMain(event) method
     */
    public void cancelBtn(ActionEvent event) {
        MainController.returnToMain(event);

    }

}
