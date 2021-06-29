/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Ability;
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
public class SupeDaoDB implements SupeDao {

    @Autowired
    SuperHeroServiceImpl service;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Supe getSupeById(int supeId) {
        try {
            final String GET_SUPE_BY_ID = "SELECT * FROM supe WHERE supeId = ?";
            Supe supe = jdbc.queryForObject(GET_SUPE_BY_ID, new SupeMapper(), supeId);
            supe.setAbilities(service.getAbilitiesForSupe(supe));
            return supe;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Supe> getAllSupes() {
        final String GET_ALL_SUPES = "SELECT * FROM supe ORDER BY name ASC";
        List<Supe> supes = jdbc.query(GET_ALL_SUPES, new SupeMapper());
        service.getAbilitiesForSupes(supes);
        return supes;
    }

    @Override
    @Transactional
    public Supe addSupe(Supe supe) {
        final String INSERT_SUPE = "INSERT INTO supe(name, description) VALUES(?,?)";
        jdbc.update(INSERT_SUPE, supe.getName(), supe.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        supe.setSupeId(newId);
        insertSupeAbility(supe);
        return supe;
    }

    @Override
    @Transactional
    public void updateSupe(Supe supe) {
        final String UPDATE_SUPE = "UPDATE supe SET name = ?, description = ? WHERE supeId = ?";
        jdbc.update(UPDATE_SUPE, supe.getName(), supe.getDescription(), supe.getSupeId());

        final String DELETE_SUPE_ABILITY = "DELETE FROM supe_ability WHERE supeId = ?";
        jdbc.update(DELETE_SUPE_ABILITY, supe.getSupeId());
        final String DELETE_SUPE_ORG = "DELETE FROM supe_org WHERE orgId = ?";
        jdbc.update(DELETE_SUPE_ORG, supe.getSupeId());
        insertSupeAbility(supe);
    }

    @Override
    public void deleteSupeById(int supeId) {
        final String DELETE_SUPE_ABILITY = "DELETE FROM supe_ability WHERE supeId = ?";
        jdbc.update(DELETE_SUPE_ABILITY, supeId);
        final String DELETE_SUPE_ORG = "DELETE FROM supe_org WHERE supeId = ?";
        jdbc.update(DELETE_SUPE_ORG, supeId);
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE supeId = ?";
        jdbc.update(DELETE_SIGHTING, supeId);
        final String DELETE_SUPE = "DELETE FROM supe WHERE supeId = ?";
        jdbc.update(DELETE_SUPE, supeId);
    }

    public static final class SupeMapper implements RowMapper<Supe> {

        @Override
        public Supe mapRow(ResultSet rs, int i) throws SQLException {
            Supe supe = new Supe();
            supe.setSupeId(rs.getInt("supeId"));
            supe.setName(rs.getString("name"));
            supe.setDescription(rs.getString("description"));
            return supe;
        }
    }

    private void insertSupeAbility(Supe supe) {
        final String INSERT_SUPE_ABILITY = "INSERT INTO supe_ability(supeId, abilityId) VALUES(?,?)";
        for (Ability ability : supe.getAbilities()) {
            jdbc.update(INSERT_SUPE_ABILITY, supe.getSupeId(), ability.getAbilityId());
        }
    }

    // private void insertSupeOrg(Supe supe) {
    //     final String INSERT_SUPE_ORG = "INSERT INTO supe_org(supeId, orgId) VALUES(?,?)";
    //     for (Org org : supe.getTeams()) {
    //         jdbc.update(INSERT_SUPE_ORG, supe.getSupeId(), org.getOrgId());
    //     }
    // }
    

}