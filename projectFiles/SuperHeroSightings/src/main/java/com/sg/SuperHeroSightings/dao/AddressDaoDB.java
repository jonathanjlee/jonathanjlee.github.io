/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Address;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Repository
public class AddressDaoDB implements AddressDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Address getAddressById(int addressId) {
        try {
            final String GET_ADDRESS_BY_ID = "SELECT * FROM address "
                    + "WHERE addressId = ?";
            return jdbc.queryForObject(GET_ADDRESS_BY_ID, new AddressMapper(), 
                    addressId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Address> getAllAddresses() {
        final String GET_ALL_ADDRESSES = "SELECT * FROM address";
        return jdbc.query(GET_ALL_ADDRESSES, new AddressMapper());
    }

    @Override
    @Transactional
    public Address addAddress(Address address) {
        final String INSERT_ADDRESS = "INSERT INTO address(street, city, state, "
                + "zipCode, coordinates) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ADDRESS, 
                address.getStreet(), 
                address.getCity(), 
                address.getState(), 
                address.getZipCode(), 
                address.getCoordinates());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        address.setAddressId(newId);
        return address;
    }

    @Override
    public void updateAddress(Address address) {
        final String UPDATE_ADDRESS = "UPDATE address SET street = ?, city = ?, "
                + "state = ?, zipCode = ?, coordinates = ? WHERE addressId = ?";
        jdbc.update(UPDATE_ADDRESS, 
                address.getStreet(), 
                address.getCity(), 
                address.getState(), 
                address.getZipCode(), 
                address.getCoordinates(), 
                address.getAddressId());
    }

    @Override
    @Transactional
    public void deleteAddressById(int addressId) {
        final String DELETE_SUPE_ORG = "DELETE so.* FROM supe_org so "
                + "JOIN org o ON so.orgId = o.orgId "
                + "WHERE addressId = ?";
        jdbc.update(DELETE_SUPE_ORG, addressId);
        
        final String DELETE_ORG_BY_ADDRESS = "DELETE FROM org WHERE addressId = ?";
        jdbc.update(DELETE_ORG_BY_ADDRESS, addressId);
        
        final String DELETE_SIGHTING_BY_ADDRESS = "DELETE s.* FROM sighting s "
                + "JOIN location l ON s.locationId = l.locationId "
                + "WHERE l.addressId = ?";
        jdbc.update(DELETE_SIGHTING_BY_ADDRESS, addressId);
        
        final String DELETE_LOCATION_BY_ADDRESS = "DELETE FROM location "
                + "WHERE addressId = ?";
        jdbc.update(DELETE_LOCATION_BY_ADDRESS, addressId);
        
        final String DELETE_ADDRESS = "DELETE FROM address WHERE addressId = ?";
        jdbc.update(DELETE_ADDRESS, addressId);
    }
    
    public static final class AddressMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int i) throws SQLException {
            Address address = new Address();
            address.setAddressId(rs.getInt("addressId"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZipCode(rs.getString("zipCode"));
            address.setCoordinates(rs.getString("coordinates"));
            return address;
        }
        
    }
    
}
