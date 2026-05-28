package com.carhire.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CustomerTest {

    @Test
    void testValidCustomer() {
        Customer customer = new Customer("1", "John Doe", 30, 10);
        assertTrue(customer.isValid());
    }

    @Test
    void testInvalidCustomer_Branches() {
         assertFalse(new Customer("2", "John", 17, 1).isValid(), "Младше 18");
        assertFalse(new Customer("3", "John", 30, -1).isValid(), "Отрицательный стаж");
        assertFalse(new Customer("4", "", 30, 5).isValid(), "Пустое имя");
        assertFalse(new Customer("5", null, 30, 5).isValid(), "Имя null");
        assertFalse(new Customer("6", "   ", 30, 5).isValid(), "Имя из пробелов");
    }

    @Test
    void testIsYoungOrInexperiencedDriver_Branches() {

        assertTrue(new Customer("1", "A", 20, 5).isYoungOrInexperiencedDriver(), "Младше 25, опытный");
        assertTrue(new Customer("2", "B", 30, 1).isYoungOrInexperiencedDriver(), "Старше 25, неопытный");
        assertTrue(new Customer("3", "C", 20, 1).isYoungOrInexperiencedDriver(), "Младше 25, неопытный");
        assertFalse(new Customer("4", "D", 26, 5).isYoungOrInexperiencedDriver(), "Старше 25, опытный (Идеальный клиент)");
    }

    @Test
    void testGetters() {

        Customer c = new Customer("ID-123", "Alice", 28, 7);
        assertEquals("ID-123", c.getId());
        assertEquals("Alice", c.getFullName());
        assertEquals(28, c.getAge());
        assertEquals(7, c.getDrivingExperienceYears());
    }
}
