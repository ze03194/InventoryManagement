package com.example.inventorymanagement.models;

/**
 * Outsourced class to serve as a model for parts that are outsourced
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Seven param Constructor to instantiate a new Outsourced object.
     *
     * @param id          holds the ID of the part
     * @param name        holds the name of the part
     * @param price       holds the price of the part
     * @param stock       holds the stock of the part
     * @param min         holds the min stock of the part
     * @param max         holds the max stock of the part
     * @param companyName holds the name of the company that outsourced the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }


    /**
     * getCompanyName() method to return the company name
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setCompanyName(String) method to set the company name
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
