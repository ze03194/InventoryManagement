package com.example.inventorymanagement.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory model to serve as storage for parts and products and perfrom Create, Retrieve, Update, and Delete functionality
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * addPart() method to add a new part to all parts list.
     *
     * @param newPart the new Part to be added to the inventory of all parts.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * addProduct() method to add a new product to all products list.
     *
     * @param newProduct the new Product to be added to the inventory of all products
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * lookupPart() method to search for a part with its given part ID.
     *
     * @param partId the ID of the Part to be searched for.
     * @return a part
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId)
                return part;

        }
        return null;
    }

    /**
     * lookupProduct(int) method to search for a product with its given product ID.
     *
     * @param prodId the ID of the Product to be searched for.
     * @return a product
     */
    public static Product lookupProduct(int prodId) {
        for (Product product : allProducts) {
            if (product.getId() == prodId)
                return product;
        }
        return null;
    }


    /**
     * lookUpPart(String) to look up a list of parts by their name
     *
     * @param partName the name of the part to search for in the list of all parts
     * @return a list of parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts)
            if (part.getName().equals(partName))
                parts.add(part);
        return parts;
    }


    /**
     * lookupProduct(String) to look up a list of products by their name.
     *
     * @param productName accepts a String as a parameter to be used to search for a list of products with the same name.
     * @return a list of products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product : allProducts)
            if (product.getName().equals(productName))
                products.add(product);
        return products;
    }


    /**
     * updatePart(int, Part) method to update a selected part by their index in the list.
     *
     * @param index        index of where the Part is in the list
     * @param selectedPart the user selected product for modification
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }


    /**
     * updateProduct(int, Product) method to update a selected product by their index in the list.
     *
     * @param index           the index of where the Product is in the list
     * @param selectedProduct the user selected product for modification
     */
    public static void updateProduct(int index, Product selectedProduct) {
        if (allProducts.contains(selectedProduct))
            allProducts.set(index, selectedProduct);
    }


    /**
     * deletePart(Part) method to delete a part selected by the user
     *
     * @param selectedPart the user selected part for deletion
     * @return a boolean
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }


    /**
     * deletePart(Product) method to delete a part selected by the user
     *
     * @param selectedProduct the user selected product
     * @return returns a boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * getAllParts() method to return a list of all parts
     *
     * @return a list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * getAllProducts() method to return a list of all products
     *
     * @return a list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
