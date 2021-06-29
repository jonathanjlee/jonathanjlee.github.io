/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Phone;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface PhoneDao {

    /**
     * Pulls a Phone using a given phoneId.
     *
     * @param phoneId
     * @return The affiliated Phone object.
     */
    Phone getPhoneById(int phoneId);

    /**
     * Pulls a list of all Phones in the database.
     *
     * @return A list of Phone objects.
     */
    List<Phone> getAllPhones();

    /**
     * Adds a Phone to the database.
     *
     * @param phone
     * @return The added Phone object.
     */
    Phone addPhone(Phone phone);

    /**
     * Updates an existing Phone in the database.
     *
     * @param phone
     */
    void updatePhone(Phone phone);

    /**
     * Deletes a PHone using a given phoneId.
     *
     * @param phoneId
     */
    void deletePhoneById(int phoneId);
}
