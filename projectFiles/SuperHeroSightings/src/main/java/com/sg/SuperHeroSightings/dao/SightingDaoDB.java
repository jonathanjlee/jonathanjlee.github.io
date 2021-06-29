/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.SuperHeroSightings.dao.LocationDaoDB.LocationMapper;
import com.sg.SuperHeroSightings.dao.SupeDaoDB.SupeMapper;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    SuperHeroServiceImpl service;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            sighting.setSupe(getSupeForSighting(sightingId));
            sighting.setLocation(getLocationForSighting(sightingId));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting ORDER BY date ASC;";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        addSupesAndLocationsToSightings(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getLast10Sightings() {
        final String GET_LAST_10_SIGHTINGS = "SELECT * FROM sighting ORDER BY date DESC LIMIT 0, 10;";
        List<Sighting> sightings = jdbc.query(GET_LAST_10_SIGHTINGS, new SightingMapper());
        addSupesAndLocationsToSightings(sightings);
        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(supeId, locationId, date) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING, sighting.getSupe().getSupeId(), sighting.getLocation().getLocationId(),
                Date.valueOf(sighting.getDate()));

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET supeId = ?, locationId = ?, date = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING, sighting.getSupe().getSupeId(), sighting.getLocation().getLocationId(),
                Date.valueOf(sighting.getDate()), sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int sightingId) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, sightingId);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }

    }

    // pull the complete Supe object that is part of the Sighting object
    private Supe getSupeForSighting(int sightingId) {
        final String GET_SUPE_FOR_SIGHTING = "SELECT su.* FROM supe su JOIN sighting si ON su.supeId = si.supeId "
                + "WHERE sightingId = ?";
        Supe supe = jdbc.queryForObject(GET_SUPE_FOR_SIGHTING, new SupeMapper(), sightingId);
        supe.setAbilities(service.getAbilitiesForSupe(supe));
        return supe;
    }

    // pull the complete Location object that is part of a Sighting object
    private Location getLocationForSighting(int sightingId) {
        final String GET_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s ON l.locationId = s.locationId WHERE sightingId = ?";
        Location location = jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationMapper(), sightingId);
        location.setAddress(service.getAddressForLocation(location));
        return location;
    }

    // add complete Supe and Location to all Sighting objects in a list
    public void addSupesAndLocationsToSightings(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setSupe(getSupeForSighting(sighting.getSightingId()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
        }
    }
}
