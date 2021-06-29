/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jonat
 */
public class FlooringProductDaoFileImplTest {

    FlooringProductDao testProductDao;

    public FlooringProductDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "test/testData/testProductFile.txt";
        new FileReader(testFile);
        testProductDao = new FlooringProductDaoFileImpl(testFile);
    }

    @Test
    public void testGetInfo() throws Exception {
        // ACT
        Map<String, Product> allProducts = testProductDao.getAllProductInfo();
        
        // ASSERT
        assertFalse(allProducts.isEmpty(), "The list of products should not "
                + "be empty.");
        assertEquals(1, allProducts.size(), "The list of products should "
                + "have 1 entry.");
    }
    
    @Test
    public void testGetCosts() throws Exception {
        // ACT
        BigDecimal productCost = testProductDao.getCostPerSquareFoot(
                "Linoleum");
        BigDecimal productLaborCost = testProductDao.getLaborCostPerSquareFoot(
                "Linoleum");

        // ASSERT
        assertEquals(new BigDecimal("2.15"), productCost, "The cost of "
                + "Linoleum is $2.15.");
        assertEquals(new BigDecimal("3.00"), productLaborCost, "It costs "
                + "$3.00 to install Linoleum.");
    }

}
