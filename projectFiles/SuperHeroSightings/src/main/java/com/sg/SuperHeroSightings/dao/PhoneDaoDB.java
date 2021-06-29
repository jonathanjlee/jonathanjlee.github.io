/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Phone;
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
public class PhoneDaoDB implements PhoneDao {

    @Autowired
    SuperHeroServiceImpl service;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Phone getPhoneById(int phoneId) {
        try {
            final String GET_PHONE_BY_ID = "SELECT * FROM phone " + "WHERE phoneId = ?";
            Phone phone = jdbc.queryForObject(GET_PHONE_BY_ID, new PhoneMapper(), phoneId);
            phone.setPhoneType(service.getPhoneTypeForPhone(phone));
            return phone;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Phone> getAllPhones() {
        final String GET_ALL_PHONES = "SELECT * FROM PHONE";
        List<Phone> phones = jdbc.query(GET_ALL_PHONES, new PhoneMapper());
        service.addPhoneTypesToPhones(phones);
        return phones;
    }

    @Override
    @Transactional
    public Phone addPhone(Phone phone) {
        final String INSERT_PHONE = "INSERT INTO phone(phoneNumber, phoneTypeId) " + "VALUES(?,?)";
        jdbc.update(INSERT_PHONE, phone.getPhoneNumber(), phone.getPhoneType().getPhoneTypeId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        phone.setPhoneId(newId);
        return phone;
    }

    @Override
    public void updatePhone(Phone phone) {
        final String UPDATE_PHONE = "UPDATE phone SET phoneNumber = ?, " + "phoneTypeId = ? where phoneId = ?";
        jdbc.update(UPDATE_PHONE, phone.getPhoneNumber(), phone.getPhoneType().getPhoneTypeId(), phone.getPhoneId());
    }

    @Override
    @Transactional
    public void deletePhoneById(int phoneId) {
        final String DELETE_ORG_PHONE = "DELETE FROM org WHERE phoneId = ?";
        jdbc.update(DELETE_ORG_PHONE, phoneId);

        final String DELETE_PHONE = "DELETE FROM phone WHERE phoneId = ?";
        jdbc.update(DELETE_PHONE, phoneId);
    }

    public static final class PhoneMapper implements RowMapper<Phone> {

        @Override
        public Phone mapRow(ResultSet rs, int i) throws SQLException {
            Phone phone = new Phone();
            phone.setPhoneId(rs.getInt("phoneId"));
            phone.setPhoneNumber(rs.getString("phoneNumber"));
            return phone;
        }

    }
}