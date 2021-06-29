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
import java.time.LocalDate;
import java.util.ArrayList;
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
public class AddressDaoDBTest {

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

    public AddressDaoDBTest() {
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
    public void testAddAndGetAddress() {
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        Address fromDao = addressDao.getAddressById(address.getAddressId());

        assertEquals(address, fromDao);
    }

    @Test
    public void testGetAllAddresses() {
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

        List<Address> addresses = addressDao.getAllAddresses();

        assertEquals(2, addresses.size());
        assertTrue(addresses.contains(address1));
        assertTrue(addresses.contains(address2));
    }

    @Test
    public void testUpdateAddress() {
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertEquals(address, fromDao);

        address.setStreet("427 S 4th Street");
        address.setCity("Louisville");
        address.setState("KY");
        address.setZipCode("40202");
        address.setCoordinates("38.25219506680315, -85.7571201939467");
        addressDao.updateAddress(address);

        assertNotEquals(address, fromDao);

        fromDao = addressDao.getAddressById(address.getAddressId());

        assertEquals(address, fromDao);

    }

    @Test
    public void testDeleteAddress() {
        // build an address to delete
        Address address1 = new Address();
        address1.setStreet("15 S 5th Street");
        address1.setCity("Minneapolis");
        address1.setState("MN");
        address1.setZipCode("55402");
        address1.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address1);

        Address address2 = new Address();
        address2.setStreet("401 Chicago Avenue");
        address2.setCity("Minneapolis");
        address2.setState("MN");
        address2.setZipCode("55415");
        address2.setCoordinates("44.974344372572276, -93.25727992065679");
        addressDao.addAddress(address2);

        // build a Supe for the Org team
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

        List<Supe> members = new ArrayList<>();
        members.add(supe);

        // build an Org to delete
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Business");
        phoneType.setPhoneTypeId(1);
        phoneTypeDao.addPhoneType(phoneType);

        Phone phone = new Phone();
        phone.setPhoneNumber("855.599.9584");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);

        Org org = new Org();
        org.setName("The Software Guild");
        org.setDescription("Coding boot camp");
        org.setAddress(address1);
        org.setPhone(phone);
        org.setMembers(members);
        orgDao.addOrg(org);

        // build a Location to delete
        Location location = new Location();
        location.setName("US Bank Stadium");
        location.setDescription("Football stadium");
        location.setAddress(address2);
        locationDao.addLocation(location);

        // build a Sighting to delete
        Sighting sighting = new Sighting();
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.addSighting(sighting);

        Address fromDaoAddress1 = addressDao.getAddressById(address1.getAddressId());
        Address fromDaoAddress2 = addressDao.getAddressById(address2.getAddressId());
        Org fromDaoOrg = orgDao.getOrgById(org.getOrgId());
        Location fromDaoLocation = locationDao.getLocationById(location.getLocationId());
        Sighting fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(address1, fromDaoAddress1);
        assertEquals(address2, fromDaoAddress2);
        assertEquals(org, fromDaoOrg);
        assertEquals(location, fromDaoLocation);
        assertEquals(sighting, fromDaoSighting);

        addressDao.deleteAddressById(address1.getAddressId());

        fromDaoAddress1 = addressDao.getAddressById(address1.getAddressId());
        fromDaoOrg = orgDao.getOrgById(org.getOrgId());

        assertNull(fromDaoAddress1);
        assertNull(fromDaoOrg);
        assertNotNull(fromDaoAddress2);
        assertNotNull(fromDaoLocation);
        assertNotNull(fromDaoSighting);

        addressDao.deleteAddressById(address2.getAddressId());

        fromDaoAddress2 = addressDao.getAddressById(address2.getAddressId());
        fromDaoLocation = locationDao.getLocationById(location.getLocationId());
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());

        assertNull(fromDaoAddress2);
        assertNull(fromDaoLocation);
        assertNull(fromDaoSighting);
    }

}
