/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jonat
 */
public class FlooringProductDaoStubImpl implements FlooringProductDao {

    public Product onlyProduct;
    
    public FlooringProductDaoStubImpl() {
        onlyProduct = new Product("Linoleum");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("2.00"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("3.15"));
    }
    
    public FlooringProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }
    
    @Override
    public Map<String, Product> getAllProductInfo() 
            throws FlooringPersistenceException {
        Map<String, Product> productList = new HashMap<>();
        productList.put(onlyProduct.getProductType(), onlyProduct);
        return productList;
    }

    @Override
    public BigDecimal getCostPerSquareFoot(String productType) 
            throws FlooringPersistenceException, InvalidProductException {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct.getCostPerSquareFoot();
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal getLaborCostPerSquareFoot(String productType) 
            throws FlooringPersistenceException, InvalidProductException {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct.getLaborCostPerSquareFoot();
        } else {
            return null;
        }
    }
    
}
