/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Ability;
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
public class AbilityDaoDB implements AbilityDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Ability getAbilityById(int abilityId) {
        try {
            final String GET_ABILITY_BY_ID = "SELECT * FROM ability " + "WHERE abilityId = ?";
            return jdbc.queryForObject(GET_ABILITY_BY_ID, new AbilityMapper(), abilityId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Ability> getAllAbilities() {
        final String GET_ALL_ABILITIES = "SELECT * FROM ability ORDER BY name ASC";
        return jdbc.query(GET_ALL_ABILITIES, new AbilityMapper());
    }

    @Override
    @Transactional
    public Ability addAbility(Ability ability) {
        final String INSERT_ABILITY = "INSERT INTO ability(name) VALUES(?)";
        jdbc.update(INSERT_ABILITY, ability.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        ability.setAbilityId(newId);
        return ability;
    }

    @Override
    public void updateAbility(Ability ability) {
        final String UPDATE_ABILITY = "UPDATE ability SET name = ? " + "WHERE abilityId = ?";
        jdbc.update(UPDATE_ABILITY, ability.getName(), ability.getAbilityId());
    }

    @Override
    public void deleteAbilityById(int abilityId) {
        final String DELETE_SUPE_ABILITY = "DELETE FROM supe_ability " + "WHERE abilityId = ?";
        jdbc.update(DELETE_SUPE_ABILITY, abilityId);

        final String DELETE_ABILITY = "DELETE FROM ability WHERE abilityId = ?";
        jdbc.update(DELETE_ABILITY, abilityId);
    }

    public static final class AbilityMapper implements RowMapper<Ability> {

        @Override
        public Ability mapRow(ResultSet rs, int i) throws SQLException {
            Ability ability = new Ability();
            ability.setAbilityId(rs.getInt("abilityId"));
            ability.setName(rs.getString("name"));
            return ability;
        }

    }
}
