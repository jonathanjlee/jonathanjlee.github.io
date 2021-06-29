/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Ability;
import com.sg.SuperHeroSightings.entities.Address;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Phone;
import com.sg.SuperHeroSightings.entities.PhoneType;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author jonat
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneTypeDaoDBTest {

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrgDao orgDao;

    @Autowired
    PhoneDao phoneDao;

    @Autowired
    PhoneTypeDao phoneTypeDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SupeDao supeDao;

    public PhoneTypeDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Ability> abilities = abilityDao.getAllAbilities();
        for (Ability ability : abilities) {
            abilityDao.deleteAbilityById(ability.getAbilityId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Org> orgs = orgDao.getAllOrgs();
        for (Org org : orgs) {
            orgDao.deleteOrgById(org.getOrgId());
        }

        List<Phone> phones = phoneDao.getAllPhones();
        for (Phone phone : phones) {
            phoneDao.deletePhoneById(phone.getPhoneId());
        }

        List<Address> addresses = addressDao.getAllAddresses();
        for (Address address : addresses) {
            addressDao.deleteAddressById(address.getAddressId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }

        List<Supe> supes = supeDao.getAllSupes();
        for (Supe supe : supes) {
            supeDao.deleteSupeById(supe.getSupeId());
        }
    }

    @Test
    public void testAddAndGetPhoneType() {
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType);

        PhoneType fromDao = phoneTypeDao.getPhoneTypeById(phoneType.getPhoneTypeId());
        assertEquals(phoneType, fromDao);
    }

}
