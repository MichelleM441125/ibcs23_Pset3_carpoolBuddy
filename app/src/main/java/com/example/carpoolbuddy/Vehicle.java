package com.example.carpoolbuddy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Vehicle {

    public String owner;
    public String model;
    public String vehicleType;
    public int capacity;
    public String vehicleID;
    public ArrayList<String> ridersIDs;
    public Boolean open;
    public Double basePrice;

    public  Vehicle()
    {

    }

    public Vehicle(String owner, String model, String vehicleType, int capacity, String vehicleID,
                   Boolean open, Double basePrice, ArrayList<String> ridersIDs)
    {
        this.owner = owner;
        this.model = model;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.vehicleID = vehicleID;
        this.ridersIDs = ridersIDs;
        this.open = open;
        this.basePrice = basePrice;

    }

    public ArrayList<String> getRidersIDs() {
        return ridersIDs;
    }

    public String getModel()
    {
        return model;
    }

    public String getVehicleType()
    {
        return vehicleType;
    }

    public Boolean getOpen()
    {
        return open;
    }

    public int getCapacity() {
        return capacity;
    }

    public Double getPrice()
    {
        return basePrice;
    }

    public String getVehicleID() {
        return vehicleID;
    }
}
