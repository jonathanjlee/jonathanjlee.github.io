/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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
public class LocationDaoDBTest {

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

    public LocationDaoDBTest() {
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
    public void testAddAndGetLocation() {
        // create an Address object for the Location object
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(address);
        locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations() {
        Address address1 = new Address();
        address1.setStreet("15 S 5th Street");
        address1.setCity("Minneapolis");
        address1.setState("MN");
        address1.setZipCode("55402");
        address1.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address1);

        Address address2 = new Address();
        address2.setStreet("427 S 4th Street");
        address2.setCity("Louisville");
        address2.setState("KY");
        address2.setZipCode("40202");
        address2.setCoordinates("38.25219506680315, -85.7571201939467");
        addressDao.addAddress(address2);

        Location location1 = new Location();
        location1.setName("The Software Guild - MN");
        location1.setDescription("Java Boot Camp - MN location");
        location1.setAddress(address1);
        locationDao.addLocation(location1);

        Location location2 = new Location();
        location2.setName("The Software Guild - KY");
        location2.setDescription("Java Boot Camp - KY location");
        location2.setAddress(address2);
        locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(address);
        locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);

        address.setStreet("427 S 4th Street");
        address.setCity("Louisville");
        address.setState("KY");
        address.setZipCode("40202");
        address.setCoordinates("38.25219506680315, -85.7571201939467");
        addressDao.updateAddress(address);

        location.setName("The Software Guild - KY");
        location.setDescription("Java Boot Camp - KY location");
        location.setAddress(address);
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getLocationId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        // build an Address for the Location
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        // build a Location to delete
        Location location = new Location();
        location.setName("The Software Guild");
        location.setDescription("Java boot camp");
        location.setAddress(address);
        locationDao.addLocation(location);

        // build a supe to delete
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

        // build a Sighting
        Sighting sighting = new Sighting();
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.addSighting(sighting);

        Address fromDaoAddress = addressDao.getAddressById(address.getAddressId());
        Location fromDaoLocation = locationDao.getLocationById(location.getLocationId());
        Sighting fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(address, fromDaoAddress);
        assertEquals(location, fromDaoLocation);
        assertEquals(sighting, fromDaoSighting);

        locationDao.deleteLocationById(location.getLocationId());

        fromDaoAddress = addressDao.getAddressById(address.getAddressId());
        fromDaoLocation = locationDao.getLocationById(location.getLocationId());
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());

        assertNotNull(fromDaoAddress);
        assertNull(fromDaoLocation);
        assertNull(fromDaoSighting);
    }

}
