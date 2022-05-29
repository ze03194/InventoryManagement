package com.example.inventorymanagement.models;

/**
 * InHouse class to serve as a model for parts that are made in-house
 */
public class InHouse extends Part {

    /**
     * machineId int to hold the machine ID if parts are made in-house
     */
    private int machineId;

    /**
     * Seven param Constructor to instantiate a new InHouse object.
     *
     * @param id        holds the ID of the part
     * @param name      holds the name of the part
     * @param price     holds the price of the part
     * @param stock     holds the stock of the part
     * @param min       holds the min stock of the part
     * @param max       holds the max stock of the part
     * @param machineId holds the machine id of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);

    }
    
    /**
     * getMachineId() method to retrieve the machine ID for parts made in-house
     *
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * setMachineId() method to set the machine ID for parts made in-house
     *
     * @param machineId the machine ID for parts made in-house
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
