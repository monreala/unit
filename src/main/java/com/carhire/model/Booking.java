package com.carhire.model;

public class Booking {
    private String bookingId;
    private Customer customer;
    private Vehicle vehicle;
    private int rentDays;
    private BookingStatus status;
    public Booking(String bookingId, Customer customer, Vehicle vehicle, int rentDays) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentDays = rentDays;
        this.status = BookingStatus.PENDING;
    }
    public boolean isValidBooking() {
        if (rentDays <= 0) return false;
        if (!customer.isValid() || !vehicle.isValid()) return false;
        if (vehicle.isNeedsMaintenance()) return false;


        if (vehicle.getCategory() == VehicleCategory.PREMIUM) {
            if (customer.getAge() < 25 || customer.getDrivingExperienceYears() < 5) {
                return false;
            }
        }
        return true;
    }
    public double calculateTotalCost(DiscountProvider discountProvider){
        if(!isValidBooking()){throw new IllegalStateException("Invalid Booking");}
        double baseCost = vehicle.getBaseDailyRate()*rentDays;
        double multiplier = switch (vehicle.getCategory()){
            case ECONOMY -> 1.0;
            case STANDARD -> 1.2;
            case SUV -> 1.5;
            case PREMIUM -> 2.0;
        };
        double costAfterMultiplier = baseCost*multiplier;
        if(customer.isYoungOrInexperiencedDriver()){
            costAfterMultiplier *= 1.20;
        }
        double discountPercentage = discountProvider.getDiscountPercentage(customer, rentDays);
        double finalCost =costAfterMultiplier-(costAfterMultiplier*(discountPercentage/100));
        return finalCost;
    }
    public void confirm(){
        if(isValidBooking()){
            this.status = BookingStatus.CONFIRMED;
        }else {
            throw new IllegalStateException("Invalid Booking");
        }
    }
    public BookingStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
}
