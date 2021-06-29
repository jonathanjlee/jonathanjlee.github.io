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
public class SupeDaoDBTest {

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

    public SupeDaoDBTest() {
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
    public void testAddAndGetSupe() {
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

        Supe fromDao = supeDao.getSupeById(supe.getSupeId());
        assertEquals(supe.getName(), fromDao.getName());
        assertEquals(supe.getDescription(), fromDao.getDescription());
        assertEquals(supe.getAbilities(), fromDao.getAbilities());
    }

    @Test
    public void testGetAllSupes() {
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

        List<Supe> supes = supeDao.getAllSupes();

        assertEquals(2, supes.size());
        assertTrue(supes.contains(supe1));
        assertTrue(supes.contains(supe2));
    }

    @Test
    public void testUpdateSupe() {
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

        Supe fromDao = supeDao.getSupeById(supe.getSupeId());
        assertEquals(supe, fromDao);

        supe.setName("Marcus Sebastian Grayson");
        supeDao.updateSupe(supe);

        assertNotEquals(supe, fromDao);

        fromDao = supeDao.getSupeById(supe.getSupeId());

        assertEquals(supe, fromDao);
    }

    @Test
    public void testDeleteSupeById() {
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

        Supe fromDao = supeDao.getSupeById(supe.getSupeId());
        assertEquals(supe, fromDao);

        supeDao.deleteSupeById(supe.getSupeId());

        fromDao = supeDao.getSupeById(supe.getSupeId());

        assertNull(fromDao);
    }
}
