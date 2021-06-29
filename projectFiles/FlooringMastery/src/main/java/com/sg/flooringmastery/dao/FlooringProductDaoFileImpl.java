/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.service.InvalidProductException;
import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author jonat
 */
public class FlooringProductDaoFileImpl implements FlooringProductDao {

    public final String PROD_FILE;
    public static final String DELIMITER = ",";

    public FlooringProductDaoFileImpl() {
        PROD_FILE = "SampleFileData/Data/Products.txt";
    }

    public FlooringProductDaoFileImpl(String productTextFile) {
        PROD_FILE = productTextFile;
    }

    private Map<String, Product> productInfo = new HashMap<>();

    private void readFromFile() throws FlooringPersistenceException {
        Scanner scanner;

        productInfo = new HashMap<>();

        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(PROD_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load order data "
                    + "into memory.", e);
        }
        String currentLine;
        String[] currentTokens;

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct = new Product(currentTokens[0]);
            currentProduct.setCostPerSquareFoot(
                    new BigDecimal(currentTokens[1]));
            currentProduct.setLaborCostPerSquareFoot(
                    new BigDecimal(currentTokens[2]));

            productInfo.put(currentProduct.getProductType(),
                    currentProduct);
        }

        scanner.close();
    }

    @Override
    public Map<String, Product> getAllProductInfo()
            throws FlooringPersistenceException {
        readFromFile();
        return productInfo;
    }

    @Override
    public BigDecimal getCostPerSquareFoot(String productType)
            throws FlooringPersistenceException, InvalidProductException {
        readFromFile();
        BigDecimal costPerSquareFoot = BigDecimal.ZERO;
        Product product = productInfo.get(productType);
        costPerSquareFoot = product.getCostPerSquareFoot();
        return costPerSquareFoot;
    }

    @Override
    public BigDecimal getLaborCostPerSquareFoot(String productType)
            throws FlooringPersistenceException, InvalidProductException {
        readFromFile();
        BigDecimal laborCostPerSquareFoot = BigDecimal.ZERO;
        Product product = productInfo.get(productType);
        laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        return laborCostPerSquareFoot;
    }

}
