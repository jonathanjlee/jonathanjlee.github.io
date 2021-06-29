/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jonat
 */
public class FlooringTaxDaoFileImplTest {
    
    FlooringTaxDao testTaxDao;
    
    public FlooringTaxDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "test/testData/testTaxFile.txt";
        new FileReader(testFile);
        testTaxDao = new FlooringTaxDaoFileImpl(testFile);
    }

    @Test
    public void testGetInfo() throws Exception {
        // ACT
        Map<String, Tax> allTaxes = testTaxDao.getAllTaxInfo();
        
        // ASSERT
        assertFalse(allTaxes.isEmpty(), "The list of taxes should not be "
                + "empty.");
        assertEquals(1, allTaxes.size(), "The list of taxes should have "
                + "1 entry.");
    }
    
    @Test
    public void testGetStateTax() throws Exception {
        // ACT
        BigDecimal taxRate = testTaxDao.getTaxRate("MN");
        
        // ASSERT
        assertEquals(new BigDecimal("6.875"), taxRate, "The MN State Sales "
                + "Tax is 6.875%.");
    }
    
}
