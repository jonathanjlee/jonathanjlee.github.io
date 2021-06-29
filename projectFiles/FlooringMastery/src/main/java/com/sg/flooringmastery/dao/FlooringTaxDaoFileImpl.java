/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.service.InvalidStateTaxException;
import com.sg.flooringmastery.dto.Tax;
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
public class FlooringTaxDaoFileImpl implements FlooringTaxDao {

    public final String TAX_FILE;
    public static final String DELIMITER = ",";

    public FlooringTaxDaoFileImpl() {
        TAX_FILE = "SampleFileData/Data/Taxes.txt";
    }
    
    public FlooringTaxDaoFileImpl(String taxTextFile) {
        TAX_FILE = taxTextFile;
    }
    
    private Map<String, Tax> taxInfo = new HashMap<>();

    private void readFromFile() throws FlooringPersistenceException {
        Scanner scanner;

        taxInfo = new HashMap<>();

        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(TAX_FILE)));
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
            Tax currentStateTax = new Tax(currentTokens[0]);
            currentStateTax.setStateName(currentTokens[1]);
            currentStateTax.setTaxRate(new BigDecimal(currentTokens[2]));

            taxInfo.put(currentStateTax.getStateAbbreviation(),
                    currentStateTax);
        }
        scanner.close();
    }

    @Override
    public Map<String, Tax> getAllTaxInfo()
            throws FlooringPersistenceException {
        readFromFile();
        return taxInfo;
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation)
            throws FlooringPersistenceException, InvalidStateTaxException {
        readFromFile();
        BigDecimal taxRate = BigDecimal.ZERO;
        Tax state = taxInfo.get(stateAbbreviation);
        taxRate = state.getTaxRate();
        return taxRate;
    }
}
