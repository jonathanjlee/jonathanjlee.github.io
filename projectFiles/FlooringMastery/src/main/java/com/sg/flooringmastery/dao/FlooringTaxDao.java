/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.service.InvalidStateTaxException;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author jonat
 */
public interface FlooringTaxDao {
    
    /**
     * Pulls all of the tax information in the tax .txt file
     * @return map with all of the values keyed to the state abbreviation
     * @throws FlooringPersistenceException 
     */
    Map<String, Tax> getAllTaxInfo() 
            throws FlooringPersistenceException;
    
    /**
     * Searches a map for the tax information associated with a given state.
     * If the state does not exist in the map, it will throw an 
     * InvalidStateTaxException
     * @param stateAbbreviation the abbreviation for the state that we are 
     * selling to
     * @return the tax rate for given state
     * @throws FlooringPersistenceException
     * @throws InvalidStateTaxException 
     */
    BigDecimal getTaxRate(String stateAbbreviation) 
            throws FlooringPersistenceException, InvalidStateTaxException;
}
