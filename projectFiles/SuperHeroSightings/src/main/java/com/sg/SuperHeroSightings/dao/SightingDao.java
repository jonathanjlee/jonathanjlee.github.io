/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Sighting;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface SightingDao {

    /**
     * Pulls a Sighting using a given sightingId.
     *
     * @param sightingId
     * @return The affiliated Sighting object.
     */
    Sighting getSightingById(int sightingId);

    /**
     * Pulls the 10 most recent Sightings in the database.
     * 
     * @return A list of Sighting objects
     */
    List<Sighting> getLast10Sightings();
    /**
     * Pulls a list of all Sightings in the database.
     *
     * @return A list of Sighting objects.
     */
    List<Sighting> getAllSightings();

    /**
     * Adds a Sighting to the database.
     *
     * @param sighting
     * @return The added Sighting object.
     */
    Sighting addSighting(Sighting sighting);

    /**
     * Updates an existing Sighting in the database.
     *
     * @param sighting
     */
    void updateSighting(Sighting sighting);

    /**
     * Deletes a Sighting using a given sightingId.
     *
     * @param sightingId
     */
    void deleteSightingById(int sightingId);

}
