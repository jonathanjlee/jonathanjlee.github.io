/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface FlooringOrderDao {
    
    /**
     * Asks the user for an order date, then goes to the OrderDao and pulls 
     * all orders for that date.
     * @param orderDate the date for the order (can be past, present, or future)
     * @return a list of all orders and their values for the requested date
     * @throws FlooringPersistenceException 
     */
    List<Order> getAllOrders(LocalDate orderDate) 
            throws FlooringPersistenceException;
    
    /**
     * Adds the given order to the system and creates a new order # for the
     * order. If this is the first order for the day, it will create a new
     * folder using the specified nomenclature. If there is already an 
     * established folder, it will add this record to the end of it.
     * @param orderDate date associated with the folder that the order will go
     * into
     * @param newOrder the order that will be added to the system
     * @return the order the was passed into the method
     * @throws FlooringPersistenceException 
     */
    Order addOrder(LocalDate orderDate, Order newOrder) 
            throws FlooringPersistenceException;
      
//    /**
//     * If this is the first order for a specified date, the method will 
//     * create the header for the file
//     * @param orderDate the date of the file
//     * @throws FlooringPersistenceException 
//     */
////    void addHeaderToFile(LocalDate orderDate) 
////            throws FlooringPersistenceException;
    
    /**
     * Gets the order specified by the user. First it searches for the order
     * date folder, and then it searches for the order number.
     * @param orderDate the date of the file
     * @param orderNumber the unique id associated with the order
     * @return the order associated with the order date and order number
     * @throws FlooringPersistenceException
     */
    Order getOrder(LocalDate orderDate, int orderNumber) 
            throws FlooringPersistenceException;
    
    /**
     * Edits the order specified by the user. First it searches for the order
     * date folder, and then it searches for the order number. It then revises
     * the order with the new information provided by the user
     * @param orderDate the date of the file
     * @param revisedOrder the new order information that is to replace the 
     * existing information
     * @return the revised order that we just replaced
     * @throws FlooringPersistenceException 
     */
    Order editOrder(LocalDate orderDate, Order revisedOrder) 
            throws FlooringPersistenceException;
    
    /**
     * Removes the order specified by the user. First it searches for the order
     * date folder, and then it searches for the order number. It then revises
     * the order with the new information provided by the user
     * @param orderDate the date of the file
     * @param orderToRemove the order that is to be removed from the system
     * @return the removed order
     * @throws FlooringPersistenceException 
     */
    Order removeOrder(LocalDate orderDate, Order orderToRemove) 
            throws FlooringPersistenceException;
    
}
