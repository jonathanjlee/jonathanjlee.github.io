/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.util.List;

import com.sg.SuperHeroSightings.entities.PhoneType;

/**
 *
 * @author jonat
 */
public interface PhoneTypeDao {

    /**
     * Pulls a PhoneType using a given phoneTypeId.
     *
     * @param phoneTypeId
     * @return The affiliated PhoneType object.
     */
    PhoneType getPhoneTypeById(int phoneTypeId);

    /**
     * Pulls a list of all PhoneTypes in the database.
     * 
     * @return A list of PhoneType objects
     */
    List<PhoneType> getAllPhoneTypes();

    /**
     * Adds a PhoneType to the Database.
     * 
     * @param phoneType
     * @return The added PhoneType object.
     */
    PhoneType addPhoneType(PhoneType phoneType);
}
