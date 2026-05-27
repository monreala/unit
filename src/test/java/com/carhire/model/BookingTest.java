package com.carhire.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BookingTest {

    @Mock
    private DiscountProvider discountProviderMock;

    private Customer normalCustomer;
    private Vehicle economyCar;

    @BeforeEach
    void setUp() {
        normalCustomer = new Customer("1", "Alice", 30, 8);
        economyCar = new Vehicle("B99", "Ford", "Fiesta", VehicleCategory.ECONOMY, 100.0, 5000);
    }

    @Test
    void testValidBookingForEconomyCar() {
        Booking booking = new Booking("B-01", normalCustomer, economyCar, 5);
        assertTrue(booking.isValidBooking());
    }

    @Test
    void testInvalidBookingForPremiumCar() {
        Customer youngCustomer = new Customer("2", "Bob", 22, 2);
        Vehicle premiumCar = new Vehicle("P01", "BMW", "X5", VehicleCategory.PREMIUM, 200.0, 1000);

        Booking booking = new Booking("B-02", youngCustomer, premiumCar, 3);
        assertFalse(booking.isValidBooking(), "Молодой водитель не может брать PREMIUM авто");
    }

    @Test
    void testCalculateTotalCost_WithMockedDiscount() {
        Booking booking = new Booking("B-03", normalCustomer, economyCar, 5);

       Mockito.when(discountProviderMock.getDiscountPercentage(normalCustomer, 5)).thenReturn(10.0);

        double finalCost = booking.calculateTotalCost(discountProviderMock);

        assertEquals(450.0, finalCost, 0.01);

        Mockito.verify(discountProviderMock, Mockito.times(1)).getDiscountPercentage(normalCustomer, 5);
    }

    @Test
    void testCalculateTotalCost_YoungDriver_NoDiscount() {
        Customer inexperienced = new Customer("3", "Charlie", 35, 1); // Стаж 1 год -> наценка 20%
        Booking booking = new Booking("B-04", inexperienced, economyCar, 2); // 2 дня по 100 = 200. + 20% = 240


        Mockito.when(discountProviderMock.getDiscountPercentage(inexperienced, 2)).thenReturn(0.0);

        double cost = booking.calculateTotalCost(discountProviderMock);

        assertEquals(240.0, cost, 0.01);
    }
}