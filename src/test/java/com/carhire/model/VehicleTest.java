package com.carhire.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class VehicleTest {
    @Test
    void testNeedsMaintenance(){
        Vehicle vehicle = new Vehicle("A123","Toyota","Camry",VehicleCategory.STANDARD,50.0,15500);
        assertTrue(vehicle.isNeedMainteance(),"govno bez TO");

        vehicle.performMaintenance();
        assertFalse(vehicle.isNeedMainteance(),"Kaifi");
        assertEquals(0, vehicle.getMileageSinceLastService());
    }
}
