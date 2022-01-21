package com.example.carpoolbuddy;

public class Vehicle {

    public String owner;
    public String model;
    public String vehicleType;
    public int capacity;
    public String vehicleID;
    public String ridersUIDs;
    public Boolean open;
    public double basePrice;

    public Vehicle(String owner, String model, String vehicleType, int capacity, String vehicleID,
                    Boolean open, double basePrice)
    {
        this.owner = owner;
        this.model = model;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.vehicleID = vehicleID;
        //this.ridersUIDs = ridersUIDs;
        this.open = open;
        this.basePrice = basePrice;

    }


}
