package com.carhire.model;

public interface DiscountProvider {
    double getDiscountPercentage(Customer customer, int rentDays);
}
