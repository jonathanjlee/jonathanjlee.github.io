/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Supe;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface SupeDao {

    /**
     * Pulls a Supe using a given supeId.
     *
     * @param supeId
     * @return The affiliated Supe object.
     */
    Supe getSupeById(int supeId);

    /**
     * Pulls a list of all Supes in the database.
     *
     * @return A list of Supe objects.
     */
    List<Supe> getAllSupes();

    /**
     * Adds a Supe to the database.
     *
     * @param supe
     * @return The added Supe object.
     */
    Supe addSupe(Supe supe);

    /**
     * Updates an existing Supe in the database.
     *
     * @param supe
     */
    void updateSupe(Supe supe);

    /**
     * Deletes a Supe using a given supeId.
     *
     * @param supeId
     */
    void deleteSupeById(int supeId);

}
