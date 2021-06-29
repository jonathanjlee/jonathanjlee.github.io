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
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
public class AbilityDaoDBTest {
    
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
    
    public AbilityDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Ability> abilities = abilityDao.getAllAbilities();
        for(Ability ability : abilities) {
            abilityDao.deleteAbilityById(ability.getAbilityId());
        }
        
        List<Address> addresses = addressDao.getAllAddresses();
        for(Address address : addresses) {
            addressDao.deleteAddressById(address.getAddressId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }
        
        List<Org> orgs = orgDao.getAllOrgs();
        for(Org org : orgs) {
            orgDao.deleteOrgById(org.getOrgId());
        }
        
        List<Phone> phones = phoneDao.getAllPhones();
        for(Phone phone : phones) {
            phoneDao.deletePhoneById(phone.getPhoneId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
        
        List<Supe> supes = supeDao.getAllSupes();
        for(Supe supe : supes) {
            supeDao.deleteSupeById(supe.getSupeId());
        }
    }

    @Test
    public void testAddAndGetAbility() {
        Ability ability = new Ability();
        ability.setName("Telekinesis");
        abilityDao.addAbility(ability);
        
        Ability fromDao = abilityDao.getAbilityById(ability.getAbilityId());
        
        assertEquals(ability, fromDao);
    }
     @Test
     public void testGetAllAbilities() {
         Ability ability1 = new Ability();
         ability1.setName("Telekinesis");
         abilityDao.addAbility(ability1);
         
         Ability ability2 = new Ability();
         ability2.setName("Invisibility");
         abilityDao.addAbility(ability2);
         
         List<Ability> abilities = abilityDao.getAllAbilities();
         
         assertEquals(2, abilities.size());
         assertTrue(abilities.contains(ability1));
         assertTrue(abilities.contains(ability2));
     }
     
     @Test
     public void testUpdateAbility() {
         Ability ability = new Ability();
         ability.setName("Telekinesis");
         abilityDao.addAbility(ability);
         
         Ability fromDao = abilityDao.getAbilityById(ability.getAbilityId());
         assertEquals(ability, fromDao);
         
         ability.setName("Weather manipulation");
         abilityDao.updateAbility(ability);
         
         assertNotEquals(ability, fromDao);
         
         fromDao = abilityDao.getAbilityById(ability.getAbilityId());
         
         assertEquals(ability, fromDao);
     }
     
     @Test
     public void testDeleteAbility() {
         Ability ability1 = new Ability();
         ability1.setName("Telekinesis");
         abilityDao.addAbility(ability1);
                  
         Ability ability2 = new Ability();
         ability2.setName("Flight");
         abilityDao.addAbility(ability2);
         
         List<Ability> abilities = new ArrayList<>();
         abilities.add(ability1);
         abilities.add(ability2);
         
         Supe supe = new Supe();
         supe.setName("Jean Grey");
         supe.setDescription("Omega Level");
         supe.setAbilities(abilities);
         supeDao.addSupe(supe);
         
         Ability fromDao = abilityDao.getAbilityById(ability1.getAbilityId());
         assertEquals(ability1, fromDao);
         
         abilityDao.deleteAbilityById(ability1.getAbilityId());
         
         fromDao = abilityDao.getAbilityById(ability1.getAbilityId());
         
         assertNull(fromDao);
     }
}
