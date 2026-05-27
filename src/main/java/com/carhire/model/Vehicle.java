package com.carhire.model;

public class Vehicle {
    private String regNumber;
    private String brand;
    private String model;
    private VehicleCategory category;
    private double baseDailyRate;
    private int mileageSinceLastService;
    public Vehicle(String regNumber, String brand, String model, VehicleCategory category, double baseDailyRate, int mileageSinceLastService) {
        this.regNumber = regNumber;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.baseDailyRate = baseDailyRate;
        this.mileageSinceLastService = mileageSinceLastService;
    }

    public boolean isNeedMainteance(){
        return mileageSinceLastService >= 15000;
    }
    public void performMaintenance(){
        this.mileageSinceLastService = 0;
    }
    public boolean isValid (){
        return baseDailyRate > 0.0 && regNumber !=null && !regNumber.trim().isEmpty();
    }
    public String getRegNumber() { return regNumber; }
    public VehicleCategory getCategory() { return category; }
    public double getBaseDailyRate() { return baseDailyRate; }
    public int getMileageSinceLastService() { return mileageSinceLastService; }

}
