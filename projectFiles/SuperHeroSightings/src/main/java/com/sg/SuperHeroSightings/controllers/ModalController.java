package com.sg.SuperHeroSightings.controllers;

import java.time.LocalDate;
import java.util.List;

import com.sg.SuperHeroSightings.dao.LocationDaoDB;
import com.sg.SuperHeroSightings.dao.SightingDaoDB;
import com.sg.SuperHeroSightings.dao.SupeDaoDB;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;
import com.sg.SuperHeroSightings.service.SuperHeroServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("modals")
public class ModalController {

    @Autowired
    SightingDaoDB sightingDao;

    @Autowired
    SupeDaoDB supeDao;

    @Autowired
    LocationDaoDB locationDao;

    @Autowired
    SuperHeroServiceImpl service;

    @GetMapping("modalSearchSightingByDate")
    public String searchSightingByDate(@RequestParam("date") String dateString, Model model) {
        LocalDate date = LocalDate.parse(dateString);
        List<Sighting> sightings = service.getSightingsByDate(date);
        List<Supe> supes = supeDao.getAllSupes();
        List<Location> locations = locationDao.getAllLocations();
        
        model.addAttribute("date", date);
        model.addAttribute("sightings", sightings);
        model.addAttribute("supes", supes);
        model.addAttribute("locations", locations);

        return "modalSearchSightingByDate";
    }
}
