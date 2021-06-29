/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jonat
 */
public class FlooringServiceLayerImplTest {

    private FlooringServiceLayer service;

    public FlooringServiceLayerImplTest() {
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringServiceLayer.class);
    }

    @Test
    public void testValidOrder() throws Exception {
        // ARRANGE
        Order order = new Order(2);
        order.setCustomerName("Jane Doe");
        order.setState("MN");
        order.setProductType("Linoleum");
        order.setArea(new BigDecimal("150"));

        // ACT
        try {
            service.addOrder(LocalDate.MAX, order);
        } catch (FlooringPersistenceException
                | BelowMinimumException
                | InvalidOrderException
                | InvalidProductException
                | InvalidStateTaxException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testCalculations() throws Exception {
        // ARRANGE
        Order order = new Order(2);
        order.setCustomerName("Jane Doe");
        order.setState("MN");
        order.setProductType("Linoleum");
        order.setArea(new BigDecimal("150"));

        // ACT
        service.calculateOrderDetails(order);

        // ASSERT
        assertEquals(new BigDecimal("300.00"), order.getMaterialCost(), "The "
                + "material cost should equal $300.00.");
        assertEquals(new BigDecimal("472.50"), order.getLaborCost(), "The "
                + "labor cost should equal $472.50.");
        assertEquals(new BigDecimal("53.11"), order.getTax(), "The tax should "
                + "equal $53.11.");
        assertEquals(new BigDecimal("825.61"), order.getTotal(), "The total "
                + "should equal $825.61.");
    }

    @Test
    public void testInvalidOrderNumber() throws Exception {
        // ARRANGE
        LocalDate orderDate = LocalDate.now().plusDays(1);

        // ACT
        try {
            service.validateOrderNumber(orderDate, 5);
            fail("Expected InvalidOrderException was not thrown.");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception thrown.");
        } catch (InvalidOrderException e) {
            return;
        }
    }

    @Test
    public void testInvalidState() throws Exception {
        // ARRANGE
        Order order = new Order(2);
        order.setCustomerName("Jane Doe");
        order.setState("WI");
        order.setProductType("Linoleum");
        order.setArea(new BigDecimal("150"));

        // ACT & ASSERT
        service.addOrder(LocalDate.MAX, order);
        try {
            service.validateStateTax(order.getState());
            fail("Expected InvalidStateTaxException was not thrown.");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception thrown.");
        } catch (InvalidStateTaxException e) {
            return;
        }
    }

    @Test
    public void testInvalidProduct() throws Exception {
        // ARRANGE
        Order order = new Order(2);
        order.setCustomerName("Jane Doe");
        order.setState("MN");
        order.setProductType("Bamboo");
        order.setArea(new BigDecimal("150"));

        // ACT & ASSERT
        service.addOrder(LocalDate.MAX, order);
        try {
            service.validateProductType(order.getProductType());
            fail("Expected InvalidProductException was not thrown.");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception thrown.");
        } catch (InvalidProductException e) {
            return;
        }
    }

    @Test
    public void testBelowMinimumArea() throws Exception {
        // ARRANGE
        Order order = new Order(2);
        order.setCustomerName("Jane Doe");
        order.setState("MN");
        order.setProductType("Linoleum");
        order.setArea(new BigDecimal("80"));

        // ACT & ASSERT
        service.addOrder(LocalDate.MAX, order);
        try {
            service.validateArea(order.getArea());
            fail("Expected BelowMinimumException was not thrown.");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception thrown.");
        } catch (BelowMinimumException e) {
            return;
        }
    }

    @Test
    public void testCreateOrderNumber() throws Exception {
        // ARRANGE
        LocalDate orderDate = LocalDate.now().plusDays(1);
        int orderNumber = service.createOrderNumber(orderDate);
        Order order = new Order(orderNumber);
        order.setCustomerName("Jane Doe");
        order.setState("MN");
        order.setProductType("Linoleum");
        order.setArea(new BigDecimal("150"));

        // ACT
        service.addOrder(orderDate, order);

        // ASSERT
        assertEquals(2, order.getOrderNumber(), "The order number is 2.");
    }
}
