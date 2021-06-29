/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface FlooringServiceLayer {
    
    List<Order> displayOrders(LocalDate orderDate) 
            throws FlooringPersistenceException;
    
    File createFileFolder(LocalDate orderDate) 
            throws FlooringPersistenceException;
    
    int createOrderNumber(LocalDate orderDate) 
            throws FlooringPersistenceException;
    
    Order calculateOrderDetails(Order order) 
            throws FlooringPersistenceException, InvalidStateTaxException, 
            InvalidProductException;
    
    void addOrder(LocalDate orderDate, Order newOrder) 
            throws FlooringPersistenceException, BelowMinimumException, 
            InvalidOrderException, InvalidProductException,
            InvalidStateTaxException;
    
    Order getOrder(LocalDate orderDate, int orderNumber) 
            throws FlooringPersistenceException, InvalidOrderException;
    
    Order editOrder(LocalDate orderDate, Order revisedOrder) 
            throws FlooringPersistenceException;
    
    Order removeOrder(LocalDate orderDate, Order orderToRemove) 
            throws FlooringPersistenceException;
        
    void validateOrderNumber(LocalDate orderDate, int orderNumber) 
            throws FlooringPersistenceException, InvalidOrderException;
    
    void validateStateTax(String stateAbbreviation) 
            throws InvalidStateTaxException, FlooringPersistenceException;
    
    void validateProductType(String productType) 
            throws InvalidProductException, FlooringPersistenceException;
    
    void validateArea(BigDecimal area) 
            throws BelowMinimumException, FlooringPersistenceException;
}
