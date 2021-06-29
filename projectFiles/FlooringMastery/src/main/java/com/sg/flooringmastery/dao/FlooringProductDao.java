/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.service.InvalidProductException;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author jonat
 */
public interface FlooringProductDao {
    
    /**
     * Pulls all of the products in the product .txt file along with their
     * prices
     * @return map with all of the values keyed to the name of the product
     * @throws FlooringPersistenceException 
     */
    Map<String, Product> getAllProductInfo() 
            throws FlooringPersistenceException;
    
    /**
     * Searches a map of all the products for the value associated with the 
     * "Cost per square foot". If the product does not exist, it will throw
     * an InvalidProductException
     * @param productType the product requested by the user
     * @return the costPerSquareFoot as a BigDecimal
     * @throws FlooringPersistenceException
     * @throws InvalidProductException 
     */
    BigDecimal getCostPerSquareFoot(String productType) 
            throws FlooringPersistenceException, InvalidProductException;
    
    /**
     * Searches a map of all the products for the value associated with the
     * "Labor cost per square foot". If the product does not exist, it will
     * throw an InvalidProductException
     * @param productType the product requested by the user
     * @return the laborCostPerSquareFoot as a BigDecimal
     * @throws FlooringPersistenceException
     * @throws InvalidProductException 
     */
    BigDecimal getLaborCostPerSquareFoot(String productType) 
            throws FlooringPersistenceException, InvalidProductException;
}
