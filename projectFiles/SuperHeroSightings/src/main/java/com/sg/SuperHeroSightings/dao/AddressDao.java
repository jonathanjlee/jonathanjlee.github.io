/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Address;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface AddressDao {

    /**
     * Pulls an Address using a given addressId.
     *
     * @param addressId
     * @return The affiliated Address object.
     */
    Address getAddressById(int addressId);

    /**
     * Pulls a list of all Addresses in the database.
     *
     * @return A list of Address objects.
     */
    List<Address> getAllAddresses();

    /**
     * Adds an Address to the database.
     *
     * @param address
     * @return The added Address object.
     */
    Address addAddress(Address address);

    /**
     * Updates an existing Address in the database.
     *
     * @param address
     */
    void updateAddress(Address address);

    /**
     * Deletes an Address using a given addressId.
     *
     * @param addressId
     */
    void deleteAddressById(int addressId);
}
