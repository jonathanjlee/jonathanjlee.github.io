/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Location;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface LocationDao {

    /**
     * Pulls a Location using a given locationId.
     *
     * @param locationId
     * @return The affiliated Location object.
     */
    Location getLocationById(int locationId);

    /**
     * Pulls a list of all Locations in the database.
     *
     * @return
     */
    List<Location> getAllLocations();

    /**
     * Adds a Location to the database.
     *
     * @param location
     * @return The added Location object.
     */
    Location addLocation(Location location);

    /**
     * Updates an existing Location in the database.
     *
     * @param location
     */
    void updateLocation(Location location);

    /**
     * Deletes a Location using a given locationId.
     *
     * @param locationId
     */
    void deleteLocationById(int locationId);

    /**
     * Pulls a list of all Supes that have been seen in the given location.
     * @param locationId
     * @return The list of all Supes in that location.
     */
//    List<Supe> getAllSupesByLocation(int locationId);
}
