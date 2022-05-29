package com.example.inventorymanagement.controllers;

import com.example.inventorymanagement.models.InHouse;
import com.example.inventorymanagement.models.Inventory;
import com.example.inventorymanagement.models.Outsourced;
import com.example.inventorymanagement.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyPartController class to implement the logic supporting the ModifyPartForm
 */
public class ModifyPartController implements Initializable {

    /**
     * Part object to hold the part readily available for modification
     */
    private Part part;

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
     * initialize controller with the user selected part to be readily available for modification
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainController controller = new MainController();
        part = controller.getModifyPart();

        if (part.getClass().equals(InHouse.class)) {
            int machineID = ((InHouse) part).getMachineId();
            inHouseRadio.setSelected(true);
            outSourcedRadio.setSelected(false);
            machineCompanyLabel.setText("Machine ID");
            machineCompanyText.setText(((Integer) machineID).toString());

        } else if (part.getClass().equals(Outsourced.class)) {
            String companyName = ((Outsourced) part).getCompanyName();
            outSourcedRadio.setSelected(true);
            inHouseRadio.setSelected(false);
            machineCompanyLabel.setText("Company Name");
            machineCompanyText.setText(companyName);
        }

        ModifyProductController.initializeItem(idText, part.getId(), nameText, part.getName(), invText,
                part.getStock(), priceText, part.getPrice(), maxText, part.getMax(), minText, part.getMin());
    }

    /**
     * savePart() method accepts and validates user input. If user input is valid, the part is then saved
     * and the user is sent back to the main form
     *
     * @param event is passed to the returnToMain(event) method to return to the main form
     */
    @FXML
    public void savePart(ActionEvent event) {
        Part modifiedPart = Inventory.lookupPart(part.getId());
        int index;
        int machineID = 0;
        int inv, min, max;

        if (modifiedPart != null) {
            index = Inventory.getAllParts().indexOf(part);
            modifiedPart.setName(nameText.getText());

            /**
             * If/else statements to call the MainController.isNumeric() method to validate each data field
             */
            if (MainController.isNumeric(invText.getText())) {
                modifiedPart.setStock(Integer.parseInt(invText.getText()));
                inv = Integer.parseInt(invText.getText());
            } else
                return;

            if (MainController.isDouble(priceText.getText()))
                modifiedPart.setPrice(Double.parseDouble(priceText.getText()));
            else
                return;

            if (MainController.isNumeric(minText.getText()))
                if (Integer.parseInt(minText.getText()) <= Integer.parseInt(maxText.getText())) {
                    modifiedPart.setMin(Integer.parseInt(minText.getText()));
                    min = Integer.parseInt(minText.getText());
                } else {
                    MainController.displayAlert("minMax");
                    return;
                }
            else
                return;

            if (MainController.isNumeric(maxText.getText())) {
                modifiedPart.setMax(Integer.parseInt(maxText.getText()));
                max = Integer.parseInt(maxText.getText());
            } else
                return;

            if (!(inv >= min && inv <= max)) {
                MainController.displayAlert("invalidInventory");
                return;
            }


            /**
             * if inHouseRadio is selected, assign the machineID and create a new inHouse part
             * else-if outSourcedRadio is selected, assign the company name and create a new outSourced part
             */
            if (inHouseRadio.isSelected()) {
                if (MainController.isNumeric(machineCompanyText.getText()))
                    machineID = Integer.parseInt(machineCompanyText.getText());
                else
                    return;

                InHouse inHouse = new InHouse(modifiedPart.getId(), modifiedPart.getName(), modifiedPart.getPrice(),
                        modifiedPart.getStock(), modifiedPart.getMin(), modifiedPart.getMax(), machineID);
                Inventory.updatePart(index, inHouse);

            } else if (outSourcedRadio.isSelected()) {
                Outsourced outsourced = new Outsourced(modifiedPart.getId(), modifiedPart.getName(), modifiedPart.getPrice(), modifiedPart.getStock(),
                        modifiedPart.getMin(), modifiedPart.getMax(), machineCompanyText.getText());

                Inventory.updatePart(index, outsourced);
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
    @FXML
    public void cancelBtn(ActionEvent event) {
        MainController.returnToMain(event);
    }


    /**
     * inHouseRadioSelector() is an onAction method that executes when the inHouseRadio button is selected
     * Upon selection, outSourcedRadio is set to false and the machineCompanyLabel is set to "Machine ID"
     */
    public void inHouseRadioSelector() {
        inHouseRadio.setSelected(true);
        outSourcedRadio.setSelected(false);
        machineCompanyLabel.setText("Machine ID");

    }


    /**
     * outSourcedRadioSelector() is an onAction method that executes when the outSourcedRadio button is selected.
     * Upon selection, inHouseRadio is set to false and the machineCompanyLabel is set to "Company Label"
     */
    public void outSourcedRadioSelector() {
        outSourcedRadio.setSelected(true);
        inHouseRadio.setSelected(false);
        machineCompanyLabel.setText("Company Name");
    }
}
