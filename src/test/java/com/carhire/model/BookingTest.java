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

    private Customer validCustomer;
    private Vehicle validVehicle;

    @BeforeEach
    void setUp() {
        validCustomer = new Customer("1", "Alice", 30, 8);
        validVehicle = new Vehicle("B99", "Ford", "Fiesta", VehicleCategory.ECONOMY, 100.0, 5000);
    }

    @Test
    void testIsValidBooking_Branches() {
        assertTrue(new Booking("1", validCustomer, validVehicle, 5).isValidBooking());

        assertFalse(new Booking("2", validCustomer, validVehicle, 0).isValidBooking());

       Customer invalidCustomer = new Customer("3", "", 15, 0);
        assertFalse(new Booking("4", invalidCustomer, validVehicle, 5).isValidBooking());

       Vehicle invalidVehicle = new Vehicle("", "Ford", "Fiesta", VehicleCategory.ECONOMY, 0, 0);
        assertFalse(new Booking("5", validCustomer, invalidVehicle, 5).isValidBooking());

        Vehicle maintenanceVehicle = new Vehicle("C1", "Lada", "Vesta", VehicleCategory.ECONOMY, 100, 20000);
        assertFalse(new Booking("6", validCustomer, maintenanceVehicle, 5).isValidBooking());

       Vehicle premiumVehicle = new Vehicle("P1", "BMW", "X5", VehicleCategory.PREMIUM, 200, 1000);
        Customer youngCustomer = new Customer("7", "Bob", 24, 2);
        assertFalse(new Booking("8", youngCustomer, premiumVehicle, 5).isValidBooking());
    }

    @Test
    void testCalculateTotalCost_SwitchBranches() {
        Mockito.when(discountProviderMock.getDiscountPercentage(Mockito.any(), Mockito.anyInt())).thenReturn(0.0);

        Booking b1 = new Booking("B1", validCustomer, new Vehicle("1", "A", "B", VehicleCategory.ECONOMY, 100, 0), 1);
        assertEquals(100.0, b1.calculateTotalCost(discountProviderMock));

        Booking b2 = new Booking("B2", validCustomer, new Vehicle("2", "A", "B", VehicleCategory.STANDARD, 100, 0), 1);
        assertEquals(120.0, b2.calculateTotalCost(discountProviderMock));

        Booking b3 = new Booking("B3", validCustomer, new Vehicle("3", "A", "B", VehicleCategory.SUV, 100, 0), 1);
        assertEquals(150.0, b3.calculateTotalCost(discountProviderMock));

        Booking b4 = new Booking("B4", validCustomer, new Vehicle("4", "A", "B", VehicleCategory.PREMIUM, 100, 0), 1);
        assertEquals(200.0, b4.calculateTotalCost(discountProviderMock));
    }

    @Test
    void testCalculateTotalCost_InvalidBookingThrowsException() {
       Booking invalidBooking = new Booking("B-Err", validCustomer, validVehicle, -1);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            invalidBooking.calculateTotalCost(discountProviderMock);
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    void testConfirmBooking() {
        Booking booking = new Booking("B1", validCustomer, validVehicle, 5);
        assertEquals(BookingStatus.PENDING, booking.getStatus());

        booking.confirm();
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }

    @Test
    void testConfirmInvalidBookingThrowsException() {
        Booking invalidBooking = new Booking("B-Err", validCustomer, validVehicle, -1);

        assertThrows(IllegalStateException.class, () -> {
            invalidBooking.confirm();
        });
    }

    @Test
    void testGetters() {
        Booking b = new Booking("B1", validCustomer, validVehicle, 5);
        assertEquals(validCustomer, b.getCustomer());
        assertEquals(validVehicle, b.getVehicle());
    }
}