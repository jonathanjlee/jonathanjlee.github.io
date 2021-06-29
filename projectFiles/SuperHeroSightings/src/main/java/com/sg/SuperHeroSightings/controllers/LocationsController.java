/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import com.sg.SuperHeroSightings.dao.AbilityDao;
import com.sg.SuperHeroSightings.dao.AddressDao;
import com.sg.SuperHeroSightings.dao.LocationDao;
import com.sg.SuperHeroSightings.dao.OrgDao;
import com.sg.SuperHeroSightings.dao.PhoneDao;
import com.sg.SuperHeroSightings.dao.PhoneTypeDao;
import com.sg.SuperHeroSightings.dao.SightingDao;
import com.sg.SuperHeroSightings.dao.SupeDao;
import com.sg.SuperHeroSightings.entities.Address;
import com.sg.SuperHeroSightings.entities.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author jonat
 */
@Controller
public class LocationsController {

    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();
    Set<ConstraintViolation<Address>> addressViolations = new HashSet<>();

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

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        List<Address> addresses = addressDao.getAllAddresses();
        model.addAttribute("locations", locations);
        model.addAttribute("addresses", addresses);
        model.addAttribute("addressErrors", addressViolations);
        model.addAttribute("locationErrors", locationViolations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocationAndAddress(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String coordinates = request.getParameter("coordinates");

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        address.setCoordinates(coordinates);

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        
        Validator validateAddress = Validation.buildDefaultValidatorFactory().getValidator();
        addressViolations = validateAddress.validate(address);

        Validator validateLocation = Validation.buildDefaultValidatorFactory().getValidator();
        locationViolations = validateLocation.validate(location);

        if(addressViolations.isEmpty() && locationViolations.isEmpty()) {
            addressDao.addAddress(address);
            locationDao.addLocation(location);
        }

        return "redirect:/locations";

    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        Address address = location.getAddress();

        model.addAttribute("location", location);
        model.addAttribute("address", address);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult locationResult, @Valid Address address, BindingResult addressResult) {
        if(addressResult.hasErrors()) {
            return "editLocation";
        }
        addressDao.updateAddress(address);
        
        if(locationResult.hasErrors()) {
            return "editLocation";
        }
        location.setAddress(address);
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }
}
