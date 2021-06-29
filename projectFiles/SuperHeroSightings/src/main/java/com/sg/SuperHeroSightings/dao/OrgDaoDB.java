/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Org;
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
public class OrgDaoDB implements OrgDao {

    @Autowired
    SuperHeroServiceImpl service;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Org getOrgById(int orgId) {
        try {
            final String GET_ORG_BY_ID = "SELECT * FROM org WHERE orgId = ?";
            Org org = jdbc.queryForObject(GET_ORG_BY_ID, new OrgMapper(), orgId);
            org.setAddress(service.getAddressForOrg(org));
            org.setPhone(service.getPhoneForOrg(org));
            org.setMembers(service.getMembersForOrg(org));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Org> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM org ORDER BY name ASC";
        List<Org> orgs = jdbc.query(GET_ALL_ORGS, new OrgMapper());
        service.getInfoForOrgs(orgs);
        return orgs;
    }

    @Override
    @Transactional
    public Org addOrg(Org org) {
        final String INSERT_ORG = "INSERT INTO org(name, description, addressId, phoneId) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORG, org.getName(), org.getDescription(), org.getAddress().getAddressId(),
                org.getPhone().getPhoneId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        insertOrgMember(org);
        return org;
    }

    @Override
    @Transactional
    public void updateOrg(Org org) {
        final String UPDATE_ORG = "UPDATE org SET name = ?, description = ?, "
                + "addressId = ?, phoneId = ? WHERE orgId = ?";
        jdbc.update(UPDATE_ORG, org.getName(), org.getDescription(), org.getAddress().getAddressId(),
                org.getPhone().getPhoneId(), org.getOrgId());

        final String DELETE_SUPE_ORG = "DELETE FROM supe_org WHERE orgId = ?";
        jdbc.update(DELETE_SUPE_ORG, org.getOrgId());
        insertOrgMember(org);
    }

    @Override
    public void deleteOrgById(int orgId) {
        final String DELETE_SUPE_ORG = "DELETE FROM supe_org WHERE orgId = ?";
        jdbc.update(DELETE_SUPE_ORG, orgId);
        final String DELETE_ORG = "DELETE FROM org WHERE orgId = ?";
        jdbc.update(DELETE_ORG, orgId);
    }

    public static final class OrgMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int i) throws SQLException {
            Org org = new Org();
            org.setOrgId(rs.getInt("orgId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            return org;
        }
    }

    private void insertOrgMember(Org org) {
        final String INSERT_SUPE_ORG = "INSERT INTO supe_org(supeId, orgId) VALUES(?,?)";
        for (Supe supe : org.getMembers()) {
            jdbc.update(INSERT_SUPE_ORG, supe.getSupeId(), org.getOrgId());
        }
    }
}
