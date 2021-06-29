/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.PhoneType;
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
public class PhoneTypeDaoDB implements PhoneTypeDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public PhoneType getPhoneTypeById(int phoneTypeId) {
        try {
            final String GET_PHONETYPE_BY_ID = "SELECT * FROM phoneType " + "WHERE phoneTypeId = ?";
            return jdbc.queryForObject(GET_PHONETYPE_BY_ID, new PhoneTypeMapper(), phoneTypeId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<PhoneType> getAllPhoneTypes() {
        final String GET_ALL_PHONETYPES = "SELECT * FROM phoneType";
        List<PhoneType> phoneTypes = jdbc.query(GET_ALL_PHONETYPES, new PhoneTypeMapper());
        return phoneTypes;
    }

    @Override
    @Transactional
    public PhoneType addPhoneType(PhoneType phoneType) {
        final String INSERT_PHONETYPE = "INSERT INTO phoneType(name) VALUES(?)";
        jdbc.update(INSERT_PHONETYPE, phoneType.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        phoneType.setPhoneTypeId(newId);
        return phoneType;
    }

    public static final class PhoneTypeMapper implements RowMapper<PhoneType> {

        @Override
        public PhoneType mapRow(ResultSet rs, int i) throws SQLException {
            PhoneType phoneType = new PhoneType();
            phoneType.setPhoneTypeId(rs.getInt("phoneTypeId"));
            phoneType.setName(rs.getString("name"));
            return phoneType;
        }

    }
}
