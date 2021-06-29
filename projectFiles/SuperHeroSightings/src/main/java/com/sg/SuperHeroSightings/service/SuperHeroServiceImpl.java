package com.sg.SuperHeroSightings.service;

import java.time.LocalDate;
import java.util.List;

import com.sg.SuperHeroSightings.dao.AbilityDaoDB.AbilityMapper;
import com.sg.SuperHeroSightings.dao.AddressDaoDB.AddressMapper;
import com.sg.SuperHeroSightings.dao.OrgDaoDB.OrgMapper;
import com.sg.SuperHeroSightings.dao.PhoneDaoDB.PhoneMapper;
import com.sg.SuperHeroSightings.dao.PhoneTypeDaoDB.PhoneTypeMapper;
import com.sg.SuperHeroSightings.dao.SightingDaoDB;
import com.sg.SuperHeroSightings.dao.SightingDaoDB.SightingMapper;
import com.sg.SuperHeroSightings.dao.SupeDaoDB.SupeMapper;
import com.sg.SuperHeroSightings.entities.Ability;
import com.sg.SuperHeroSightings.entities.Address;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Phone;
import com.sg.SuperHeroSightings.entities.PhoneType;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    SightingDaoDB sightingDao;

    @Override
    public List<Org> getOrgsForSupe(Supe supe) {
        final String GET_ORGS_FOR_SUPE = "SELECT o.* FROM org o JOIN supe_org so ON o.orgId = so.orgId "
                + "WHERE so.supeId = ?";
        List<Org> orgs = jdbc.query(GET_ORGS_FOR_SUPE, new OrgMapper(), supe.getSupeId());
        getInfoForOrgs(orgs);
        return orgs;
    }

    @Override
    public Address getAddressForOrg(Org org) {
        final String GET_ADDRESS_FOR_ORG = "SELECT a.* FROM address a JOIN org o ON a.addressId = o.addressId "
                + "WHERE orgId = ?";
        return jdbc.queryForObject(GET_ADDRESS_FOR_ORG, new AddressMapper(), org.getOrgId());
    }

    @Override
    public Phone getPhoneForOrg(Org org) {
        final String GET_PHONE_FOR_ORG = "SELECT p.* FROM phone p JOIN org o ON p.phoneId = o.phoneId WHERE o.orgId = ?";
        Phone phone = jdbc.queryForObject(GET_PHONE_FOR_ORG, new PhoneMapper(), org.getOrgId());
        phone.setPhoneType(getPhoneTypeForPhone(phone));
        return phone;
    }

    @Override
    public PhoneType getPhoneTypeForPhone(Phone phone) {
        final String GET_PHONETYPE_FOR_PHONE = "SELECT pt.* FROM phoneType pt JOIN phone p ON pt.phoneTypeId = p.phoneTypeId "
                + "WHERE p.phoneId = ?";
        return jdbc.queryForObject(GET_PHONETYPE_FOR_PHONE, new PhoneTypeMapper(), phone.getPhoneId());
    }

    @Override
    public void addPhoneTypesToPhones(List<Phone> phones) {
        for (Phone phone : phones) {
            phone.setPhoneType(getPhoneTypeForPhone(phone));
        }
    }

    @Override
    public void getInfoForOrgs(List<Org> orgs) {
        for (Org org : orgs) {
            org.setAddress(getAddressForOrg(org));
            org.setPhone(getPhoneForOrg(org));
            org.setMembers(getMembersForOrg(org));
        }
    }

    @Override
    public List<Supe> getMembersForOrg(Org org) {
        final String GET_MEMBERS_FOR_TEAM = "SELECT s.* FROM supe s JOIN supe_org so ON s.supeId = so.supeId WHERE so.orgId = ?";
        List<Supe> members = jdbc.query(GET_MEMBERS_FOR_TEAM, new SupeMapper(), org.getOrgId());
        getAbilitiesForSupes(members);
        return members;
    }

    @Override
    public void getAbilitiesForSupes(List<Supe> supes) {
        for (Supe supe : supes) {
            supe.setAbilities(getAbilitiesForSupe(supe));
        }
    }

    // @Override
    // public void getOrgsForSupes(List<Supe> supes) {
    //     for (Supe supe : supes) {
    //         supe.setTeams(getOrgsForSupe(supe));
    //     }
    // }

    @Override
    public List<Ability> getAbilitiesForSupe(Supe supe) {
        final String GET_ABILITIES_FOR_SUPE = "SELECT a.* FROM ability a JOIN supe_ability sa ON a.abilityId = sa.abilityId WHERE sa.supeId = ?";
        return jdbc.query(GET_ABILITIES_FOR_SUPE, new AbilityMapper(), supe.getSupeId());
    }

    @Override
    public Address getAddressForLocation(Location location) {
        final String GET_ADDRESS_FOR_LOCATION = "SELECT a.* FROM address a JOIN location l ON a.addressId = l.addressId WHERE l.locationId = ?";
        return jdbc.queryForObject(GET_ADDRESS_FOR_LOCATION, new AddressMapper(), location.getLocationId());
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        final String GET_SIGHTING_BY_DATE = "SELECT s.* FROM sighting s WHERE s.date = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_DATE, new SightingMapper(), date);
        getInfoForSightings(sightings);
        return sightings;
    }

    @Override
    public void getInfoForSightings(List<Sighting> sightings) {
        sightingDao.addSupesAndLocationsToSightings(sightings);
    }

}