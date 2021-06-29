package com.sg.SuperHeroSightings.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sg.SuperHeroSightings.dao.AbilityDao;
import com.sg.SuperHeroSightings.dao.AddressDao;
import com.sg.SuperHeroSightings.dao.LocationDao;
import com.sg.SuperHeroSightings.dao.OrgDao;
import com.sg.SuperHeroSightings.dao.PhoneDao;
import com.sg.SuperHeroSightings.dao.PhoneTypeDao;
import com.sg.SuperHeroSightings.dao.SightingDao;
import com.sg.SuperHeroSightings.dao.SupeDao;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;
import com.sg.SuperHeroSightings.service.SuperHeroServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SightingsController {
    
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

    @Autowired
    SuperHeroServiceImpl service;
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Supe> supes = supeDao.getAllSupes();
        List<Location> locations = locationDao.getAllLocations();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("supes", supes);
        model.addAttribute("locations", locations);
        model.addAttribute("sightings", sightings);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String dateString = request.getParameter("dateOfSighting");
        Supe supe = supeDao.getSupeById(Integer.parseInt(request.getParameter("supeSighted")));
        Location location = locationDao.getLocationById(Integer.parseInt(request.getParameter("locationSighted")));

        LocalDate date = LocalDate.parse(dateString);

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int sightingId = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(sightingId);
        List<Supe> supes = supeDao.getAllSupes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("supes", supes);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int sightingId = Integer.parseInt(request.getParameter("sightingId"));
        Sighting sighting = sightingDao.getSightingById(sightingId);
        Supe supe = supeDao.getSupeById(Integer.parseInt(request.getParameter("supeId")));
        Location location = locationDao.getLocationById(Integer.parseInt(request.getParameter("locationId")));

        sighting.setDate(LocalDate.parse(request.getParameter("dateSighted")));
        sighting.setSupe(supe);
        sighting.setLocation(location);
        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }
}
