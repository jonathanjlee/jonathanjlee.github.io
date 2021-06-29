/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jonat
 */
public class FlooringOrderDaoFileImplTest {
    
    FlooringOrderDao testOrderDao;
    
    public FlooringOrderDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "test/orders/Orders_";
        
        new FileWriter(testFile);
        testOrderDao = new FlooringOrderDaoFileImpl(testFile);
    }
    
    @Test
    public void testAddGetOrder() throws Exception {
        // ARRANGE
        LocalDate orderDate = LocalDate.now().plusDays(1);
        int orderNumber = 1;
        Order order = new Order(orderNumber);
        order.setCustomerName("Jonathan Lee");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("350.00"));
        order.setLaborCost(new BigDecimal("415.00"));
        order.setTax(new BigDecimal("191.25"));
        order.setTotal(new BigDecimal("956.25"));
        
        // ACT
        testOrderDao.addOrder(orderDate, order);
        Order retrievedOrder = testOrderDao.getOrder(orderDate, orderNumber);
        
        // ASSERT
        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), 
                "Checking order number.");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), 
                "Checking customer name.");
        assertEquals(order.getState(), retrievedOrder.getState(), 
                "Checking state.");
        assertEquals(order.getProductType(), retrievedOrder.getProductType(), 
                "Checking product type.");
        assertEquals(order.getArea(), retrievedOrder.getArea(), "Checking "
                + "area.");
    }
    
    @Test
    public void testAddEditOrder() throws Exception {
        // ARRANGE
        LocalDate orderDate = LocalDate.now().plusDays(1);
        int orderNumber = 1;
        Order order = new Order(orderNumber);
        order.setCustomerName("Jonathan Lee");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("350.00"));
        order.setLaborCost(new BigDecimal("415.00"));
        order.setTax(new BigDecimal("191.25"));
        order.setTotal(new BigDecimal("956.25"));
        Order newInfo = new Order(1);
        newInfo.setCustomerName("Joe Bostrom");
        newInfo.setState("KY");
        newInfo.setTaxRate(new BigDecimal("6.00"));
        newInfo.setProductType("Carpet");
        newInfo.setArea(new BigDecimal("300"));
        newInfo.setCostPerSquareFoot(new BigDecimal("2.25"));
        newInfo.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        newInfo.setMaterialCost(new BigDecimal("675.00"));
        newInfo.setLaborCost(new BigDecimal("630.00"));
        newInfo.setTax(new BigDecimal("78.30"));
        newInfo.setTotal(new BigDecimal("1383.30"));
        
        // ACT
        testOrderDao.addOrder(orderDate, order);
        Order revisedOrder = testOrderDao.editOrder(orderDate, newInfo);
        
        // ASSERT
        assertEquals(order.getOrderNumber(), revisedOrder.getOrderNumber(), 
                "Checking order number.");
        assertEquals(order.getCustomerName(), revisedOrder.getCustomerName(), 
                "Checking customer name.");
        assertEquals(order.getState(), revisedOrder.getState(), 
                "Checking state.");
        assertEquals(order.getProductType(), revisedOrder.getProductType(), 
                "Checking product type.");
        assertEquals(order.getArea(), revisedOrder.getArea(), "Checking "
                + "area.");
    }
    
    @Test
    public void testAddGetAllRemove() throws Exception {
        // ARRANGE
        LocalDate orderDate = LocalDate.now().plusDays(1);
        Order orderOne = new Order(0001);
        orderOne.setCustomerName("Jonathan Lee");
        orderOne.setState("CA");
        orderOne.setTaxRate(new BigDecimal("25.00"));
        orderOne.setProductType("Tile");
        orderOne.setArea(new BigDecimal("100"));
        orderOne.setCostPerSquareFoot(new BigDecimal("3.50"));
        orderOne.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        orderOne.setMaterialCost(new BigDecimal("350.00"));
        orderOne.setLaborCost(new BigDecimal("415.00"));
        orderOne.setTax(new BigDecimal("191.25"));
        orderOne.setTotal(new BigDecimal("956.25"));
        Order orderTwo = new Order(0002);
        orderTwo.setCustomerName("Joe Bostrom");
        orderTwo.setState("KY");
        orderTwo.setTaxRate(new BigDecimal("6.00"));
        orderTwo.setProductType("Carpet");
        orderTwo.setArea(new BigDecimal("300"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("2.25"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        orderTwo.setMaterialCost(new BigDecimal("675.00"));
        orderTwo.setLaborCost(new BigDecimal("630.00"));
        orderTwo.setTax(new BigDecimal("78.30"));
        orderTwo.setTotal(new BigDecimal("1383.30"));
        
        // ACT & ASSERT
        testOrderDao.addOrder(orderDate, orderOne);
        testOrderDao.addOrder(orderDate, orderTwo);
        
        Order removedOrder = testOrderDao.removeOrder(orderDate, orderOne);
        
        assertEquals(removedOrder, orderOne, "The removed order should be "
                + "for Jonathan.");
        
        List<Order> allOrders = testOrderDao.getAllOrders(orderDate);
        
        assertNotNull(allOrders, "All orders list should not be null.");
        assertEquals(1, allOrders.size(), "All orders list should only have "
                + "one order.");
        assertFalse(allOrders.contains(orderOne), "All orders should NOT "
                + "include Jonathan.");
        assertTrue(allOrders.contains(orderTwo), "All orders shoudl include "
                + "Joe.");
        
        removedOrder = testOrderDao.removeOrder(orderDate, orderTwo);
        
        assertEquals(removedOrder, orderTwo, "The removed order should be for "
                + "Joe.");
        
        allOrders = testOrderDao.getAllOrders(orderDate);
        
        assertTrue(allOrders.isEmpty(), "The list should be empty.");
        
        Order retrievedOrder = 
                testOrderDao.getOrder(orderDate, orderOne.getOrderNumber());
        assertNull(retrievedOrder, "Jonathan was removed. Should be null.");
        retrievedOrder = 
                testOrderDao.getOrder(orderDate, orderTwo.getOrderNumber());
        assertNull(retrievedOrder, "Joe was removed. Should be null.");
    }
}
