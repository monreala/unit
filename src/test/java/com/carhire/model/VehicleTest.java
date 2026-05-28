package com.carhire.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testNeedsMaintenance() {
        Vehicle vehicle = new Vehicle("A123", "Toyota", "Camry", VehicleCategory.STANDARD, 50.0, 15000);
        assertTrue(vehicle.isNeedsMaintenance());

        vehicle.performMaintenance();
        assertFalse(vehicle.isNeedsMaintenance());
    }

    @Test
    void testIsValid_Branches() {
        assertTrue(new Vehicle("A123", "Toyota", "Camry", VehicleCategory.STANDARD, 50.0, 0).isValid());

        assertFalse(new Vehicle("A123", "Toyota", "Camry", VehicleCategory.STANDARD, 0.0, 0).isValid(), "Ставка <= 0");
        assertFalse(new Vehicle("", "Toyota", "Camry", VehicleCategory.STANDARD, 50.0, 0).isValid(), "Пустой номер");
        assertFalse(new Vehicle(null, "Toyota", "Camry", VehicleCategory.STANDARD, 50.0, 0).isValid(), "Номер null");
    }

    @Test
    void testGetters() {
        Vehicle v = new Vehicle("B99", "Ford", "Fiesta", VehicleCategory.ECONOMY, 100.0, 5000);
        assertEquals("B99", v.getRegNumber());
        assertEquals(VehicleCategory.ECONOMY, v.getCategory());
        assertEquals(100.0, v.getBaseDailyRate());
        assertEquals(5000, v.getMileageSinceLastService());
    }
}
