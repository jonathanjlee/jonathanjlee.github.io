/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.service.SuperHeroServiceImpl;

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
public class LocationDaoDB implements LocationDao {

    @Autowired
    SuperHeroServiceImpl service;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM location " + "WHERE locationId = ?";
            Location location = jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), locationId);
            location.setAddress(service.getAddressForLocation(location));
            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM location ORDER BY name ASC";
        List<Location> locations = jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
        addAddressesToAllLocations(locations);
        return locations;
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, " + "addressId) VALUES(?,?,?)";
        jdbc.update(INSERT_LOCATION, location.getName(), location.getDescription(),
                location.getAddress().getAddressId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, "
                + "description = ?, addressId =? WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION, location.getName(), location.getDescription(),
                location.getAddress().getAddressId(), location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) {
        final String DELETE_SIGHTING_LOCATION = "DELETE FROM sighting " + "WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING_LOCATION, locationId);

        final String DELETE_LOCATION = "DELETE FROM location " + "WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            return location;
        }
    }

    // pull the Address object and add it to all of the Locations
    private void addAddressesToAllLocations(List<Location> locations) {
        for (Location location : locations) {
            location.setAddress(service.getAddressForLocation(location));
        }
    }

}
