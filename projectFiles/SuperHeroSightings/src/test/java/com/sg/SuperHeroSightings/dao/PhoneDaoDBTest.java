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
public class PhoneDaoDBTest {
    
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
    
    public PhoneDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Ability> abilities = abilityDao.getAllAbilities();
        for(Ability ability : abilities) {
            abilityDao.deleteAbilityById(ability.getAbilityId());
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
        
        List<Address> addresses = addressDao.getAllAddresses();
        for(Address address : addresses) {
            addressDao.deleteAddressById(address.getAddressId());
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
    public void testAddAndGetPhone() {
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType);
        
        Phone phone = new Phone();
        phone.setPhoneNumber("800.123.4567");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);
        
        Phone fromDao = phoneDao.getPhoneById(phone.getPhoneId());
        
        assertEquals(phone, fromDao);
    }
    
    @Test
    public void testGetAllPhones() {
        PhoneType phoneType1 = new PhoneType();
        phoneType1.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType1);
        
        Phone phone1 = new Phone();
        phone1.setPhoneNumber("800.123.4567");
        phone1.setPhoneType(phoneType1);
        phoneDao.addPhone(phone1);
        
        PhoneType phoneType2 = new PhoneType();
        phoneType2.setName("Business");
        phoneTypeDao.addPhoneType(phoneType2);
        
        Phone phone2 = new Phone();
        phone2.setPhoneNumber("800.7654.321");
        phone2.setPhoneType(phoneType2);
        phoneDao.addPhone(phone2);
        
        List<Phone> phones = phoneDao.getAllPhones();
        
        assertEquals(2, phones.size());
        assertTrue(phones.contains(phone1));
        assertTrue(phones.contains(phone2));
    }
    
    @Test
    public void testUpdatePhone() {
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType);
        
        Phone phone = new Phone();
        phone.setPhoneNumber("800.123.4567");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);
        
        Phone fromDao = phoneDao.getPhoneById(phone.getPhoneId());
        assertEquals(phone, fromDao);
        
        phone.setPhoneNumber("800.765.4321");
        phoneDao.updatePhone(phone);
        
        assertNotEquals(phone, fromDao);
        
        fromDao = phoneDao.getPhoneById(phone.getPhoneId());
        
        assertEquals(phone, fromDao);
    }
    
    @Test
    public void testDeletePhoneById() {
        PhoneType phoneType = new PhoneType();
        phoneType.setName("Cellular");
        phoneTypeDao.addPhoneType(phoneType);
        
        Phone phone = new Phone();
        phone.setPhoneNumber("800.123.4567");
        phone.setPhoneType(phoneType);
        phoneDao.addPhone(phone);
        
        Phone fromDao = phoneDao.getPhoneById(phone.getPhoneId());
        assertEquals(phone, fromDao);
        
        phoneDao.deletePhoneById(phone.getPhoneId());
        
        fromDao = phoneDao.getPhoneById(phone.getPhoneId());
        
        assertNull(fromDao);
    }
}
