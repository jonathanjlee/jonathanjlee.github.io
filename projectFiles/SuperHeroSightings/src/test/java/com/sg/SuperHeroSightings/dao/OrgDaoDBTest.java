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
public class OrgDaoDBTest {

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

    public OrgDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Ability> abilities = abilityDao.getAllAbilities();
        for (Ability ability : abilities) {
            abilityDao.deleteAbilityById(ability.getAbilityId());
        }

        List<Address> addresses = addressDao.getAllAddresses();
        for (Address address : addresses) {
            addressDao.deleteAddressById(address.getAddressId());
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
    public void testAddAndGetOrg() {
        // build a Supe object for the Org
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
        
        // build an Address object for the Org
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        // build a Phone object for the Org
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Business");
        phoneTypeDao.addPhoneType(phoneType);

        Phone phone = new Phone();
        phone.setPhoneNumber("855.599.9584");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);

        // build an Org object
        Org org = new Org();
        org.setName("The Software Guild");
        org.setDescription("Coding boot camp");
        org.setAddress(address);
        org.setPhone(phone);
        org.setMembers(members);
        orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(org.getOrgId());

        assertEquals(org, fromDao);
    }

    @Test
    public void testGetAllOrgs() {
        // build a Supe object for the Org
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

        List<Supe> members1 = new ArrayList<>();
        members1.add(supe1);

        // build an Address object for the Org1
        Address address1 = new Address();
        address1.setStreet("15 S 5th Street");
        address1.setCity("Minneapolis");
        address1.setState("MN");
        address1.setZipCode("55402");
        address1.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address1);

        // build a Phone object for the Org1
        PhoneType phoneType1 = new PhoneType();
        phoneType1.setName("Business");
        phoneTypeDao.addPhoneType(phoneType1);

        Phone phone1 = new Phone();
        phone1.setPhoneNumber("855.599.9584");
        phone1.setPhoneType(phoneType1);
        phoneDao.addPhone(phone1);

        // build an Org1 object
        Org org1 = new Org();
        org1.setName("The Software Guild - MN");
        org1.setDescription("Coding boot camp - MN");
        org1.setAddress(address1);
        org1.setPhone(phone1);
        org1.setMembers(members1);
        orgDao.addOrg(org1);

        // build a Supe object for the Org2
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

        List<Supe> members2 = new ArrayList<>();
        members2.add(supe2);

        // build an Address object for the Org2
        Address address2 = new Address();
        address2.setStreet("427 S 4th Street");
        address2.setCity("Louisville");
        address2.setState("KY");
        address2.setZipCode("40202");
        address2.setCoordinates("38.25219506680315, -85.7571201939467");
        addressDao.addAddress(address2);

        // build a Phone object for the Org2
        PhoneType phoneType2 = new PhoneType();
        phoneType2.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType2);

        Phone phone2 = new Phone();
        phone2.setPhoneNumber("800.123.4567");
        phone2.setPhoneType(phoneType2);
        phoneDao.addPhone(phone2);

        // build an Org2 object
        Org org2 = new Org();
        org2.setName("The Software Guild - KY");
        org2.setDescription("Coding boot camp - KY");
        org2.setAddress(address2);
        org2.setPhone(phone2);
        org2.setMembers(members2);
        orgDao.addOrg(org2);

        List<Org> orgs = orgDao.getAllOrgs();

        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org1));
        assertTrue(orgs.contains(org2));
    }

    @Test
    public void testUpdateOrg() {
        // build a Supe object for the Org
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

        // build an Address object for the Org
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        // build a Phone object for the Org
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Business");
        phoneTypeDao.addPhoneType(phoneType);

        Phone phone = new Phone();
        phone.setPhoneNumber("855.599.9584");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);

        // build an Org object
        Org org = new Org();
        org.setName("The Software Guild - MN");
        org.setDescription("Coding boot camp - MN");
        org.setAddress(address);
        org.setPhone(phone);
        org.setMembers(members);
        orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(org.getOrgId());
        assertEquals(org, fromDao);

        org.setName("The Software Guild");
        org.setDescription("Coding boot camp");
        org.setAddress(address);
        org.setPhone(phone);
        org.setMembers(members);
        orgDao.updateOrg(org);

        assertNotEquals(org, fromDao);

        fromDao = orgDao.getOrgById(org.getOrgId());

        assertEquals(org, fromDao);
    }

    @Test
    public void testDeleteOrgById() {
        // build a Supe object for the Org
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

        // build an Address object for the Org
        Address address = new Address();
        address.setStreet("15 S 5th Street");
        address.setCity("Minneapolis");
        address.setState("MN");
        address.setZipCode("55402");
        address.setCoordinates("44.97912873184054, -93.27182299395133");
        addressDao.addAddress(address);

        // build a Phone object for the Org
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Business");
        phoneTypeDao.addPhoneType(phoneType);

        Phone phone = new Phone();
        phone.setPhoneNumber("855.599.9584");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);

        // build an Org object
        Org org = new Org();
        org.setName("The Software Guild - MN");
        org.setDescription("Coding boot camp - MN");
        org.setAddress(address);
        org.setPhone(phone);
        org.setMembers(members);
        orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(org.getOrgId());
        assertEquals(org, fromDao);

        orgDao.deleteOrgById(org.getOrgId());

        fromDao = orgDao.getOrgById(org.getOrgId());

        assertNull(fromDao);

    }

}
