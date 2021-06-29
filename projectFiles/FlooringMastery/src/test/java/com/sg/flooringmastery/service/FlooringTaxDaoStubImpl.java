/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jonat
 */
public class FlooringTaxDaoStubImpl implements FlooringTaxDao {

    public Tax onlyState;

    public FlooringTaxDaoStubImpl() {
        onlyState = new Tax("MN");
        onlyState.setStateName("Minnesota");
        onlyState.setTaxRate(new BigDecimal("6.875"));
    }

    public FlooringTaxDaoStubImpl(Tax testState) {
        this.onlyState = testState;
    }

    @Override
    public Map<String, Tax> getAllTaxInfo()
            throws FlooringPersistenceException {
        Map<String, Tax> taxList = new HashMap<>();
        taxList.put(onlyState.getStateAbbreviation(), onlyState);
        return taxList;
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation)
            throws FlooringPersistenceException, InvalidStateTaxException {
        if (stateAbbreviation.equals(onlyState.getStateAbbreviation())) {
            return onlyState.getTaxRate();
        } else {
            return null;
        }
    }

}
