/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Ability;
import com.sg.SuperHeroSightings.entities.Address;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Phone;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class SightingDaoDBTest {

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

    public SightingDaoDBTest() {
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
    public void testAddAndGetSighting() {
        // build a Supe object for the Sighting
        Ability ability = new Ability();
        ability.setName("Invincibility");
        abilityDao.addAbility(ability);

        List<Ability> abilities = new ArrayList<>();
        abilities.add(ability);

        Supe supe = new Supe();
        supe.setName("Superman");
        supe.setDescription("Man of Steel");
        supe.setAbilities(abilities);
        supeDao.addSupe(supe);

        // build a Location object for the Sighting
        Address sightingAddress = new Address();
        sightingAddress.setStreet("15 S 5th Street");
        sightingAddress.setCity("Minneapolis");
        sightingAddress.setState("MN");
        sightingAddress.setZipCode("55402");
        sightingAddress.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(sightingAddress);

        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(sightingAddress);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {
        // build a Supe object for the Sighting1
        Ability ability1 = new Ability();
        ability1.setName("Invincibility");
        abilityDao.addAbility(ability1);
        List<Ability> abilities1 = new ArrayList<>();
        abilities1.add(ability1);

        Supe supe1 = new Supe();
        supe1.setName("Superman");
        supe1.setDescription("Man of Steel");
        supe1.setAbilities(abilities1);
        supeDao.addSupe(supe1);

        // build a Location1 object for the Sighting1
        Address sightingAddress1 = new Address();
        sightingAddress1.setStreet("15 S 5th Street");
        sightingAddress1.setCity("Minneapolis");
        sightingAddress1.setState("MN");
        sightingAddress1.setZipCode("55402");
        sightingAddress1.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(sightingAddress1);

        Location location1 = new Location();
        location1.setName("The Software Guild");
        location1.setDescription("Java boot camp");
        location1.setAddress(sightingAddress1);
        locationDao.addLocation(location1);

        Sighting sighting1 = new Sighting();
        sighting1.setSupe(supe1);
        sighting1.setLocation(location1);
        sighting1.setDate(LocalDate.now());
        sightingDao.addSighting(sighting1);

        // build a Supe object for the Sighting2
        Ability ability2 = new Ability();
        ability2.setName("Telekinesis");
        abilityDao.addAbility(ability2);
        List<Ability> abilities2 = new ArrayList<>();
        abilities2.add(ability2);

        Supe supe2 = new Supe();
        supe2.setName("Jean Grey");
        supe2.setDescription("Omega Level");
        supe2.setAbilities(abilities2);
        supeDao.addSupe(supe2);

        // build a Location object for the Sighting
        Address sightingAddress2 = new Address();
        sightingAddress2.setStreet("427 S 4th Street");
        sightingAddress2.setCity("Louisville");
        sightingAddress2.setState("KY");
        sightingAddress2.setZipCode("40202");
        sightingAddress2.setCoordinates("38.25219506680315, -85.7571201939467");
        addressDao.addAddress(sightingAddress2);

        Location location2 = new Location();
        location2.setName("The Software Guild - KY");
        location2.setDescription("Java Boot Camp - KY location");
        location2.setAddress(sightingAddress2);
        locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setSupe(supe2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.now());
        sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
        // build a Supe object for the Sighting
        Ability ability = new Ability();
        ability.setName("Invincibility");
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = new ArrayList<>();
        abilities.add(ability);

        Supe supe = new Supe();
        supe.setName("Superman");
        supe.setDescription("Man of Steel");
        supe.setAbilities(abilities);
        supeDao.addSupe(supe);

        // build a Location object for the Sighting
        Address sightingAddress = new Address();
        sightingAddress.setStreet("15 S 5th Street");
        sightingAddress.setCity("Minneapolis");
        sightingAddress.setState("MN");
        sightingAddress.setZipCode("55402");
        sightingAddress.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(sightingAddress);

        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(sightingAddress);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);

        Ability ability2 = new Ability();
        ability2.setName("Flight");
        ability2 = abilityDao.addAbility(ability2);
        List<Ability> abilities2 = new ArrayList<>();
        abilities2.add(ability2);

        Supe supe2 = new Supe();
        supe2.setName("Wonder Woman");
        supe2.setDescription("Amazonian");
        supe2.setAbilities(abilities2);
        supeDao.addSupe(supe2);

        sighting.setSupe(supe2);
        sightingDao.updateSighting(sighting);

        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSightingById() {
        // build a Supe object for the Sighting
        Ability ability = new Ability();
        ability.setName("Invincibility");
        abilityDao.addAbility(ability);
        List<Ability> abilities = new ArrayList<>();
        abilities.add(ability);

        Supe supe = new Supe();
        supe.setName("Superman");
        supe.setDescription("Man of Steel");
        supe.setAbilities(abilities);
        supeDao.addSupe(supe);

        // build a Location object for the Sighting
        Address sightingAddress = new Address();
        sightingAddress.setStreet("15 S 5th Street");
        sightingAddress.setCity("Minneapolis");
        sightingAddress.setState("MN");
        sightingAddress.setZipCode("55402");
        sightingAddress.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(sightingAddress);

        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(sightingAddress);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getSightingId());

        fromDao = sightingDao.getSightingById(sighting.getSightingId());

        assertNull(fromDao);
    }
}
