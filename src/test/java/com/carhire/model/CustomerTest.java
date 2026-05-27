package com.carhire.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CustomerTest {

    @Test
    void testValidCustomer(){
        Customer customer = new Customer("1","John",30,10);
        assertTrue(customer.isValid(), "Customer is valid");
    }
    @Test
    void testInvalidCustomer_Underage(){
        Customer customer = new Customer("1","Maloletka",17,0);
        assertFalse(customer.isValid(), "Customer is not valid");
    }
    @Test
    void testIsYoungOrInexperiencedDriver(){
        Customer inexperienced = new Customer("1","Debil bez opita",30,1);
        assertTrue(inexperienced.isYoungOrInexperiencedDriver());
        Customer experienced = new Customer("1","norm chel",28,6);
        assertFalse(experienced.isYoungOrInexperiencedDriver());
    }
}
