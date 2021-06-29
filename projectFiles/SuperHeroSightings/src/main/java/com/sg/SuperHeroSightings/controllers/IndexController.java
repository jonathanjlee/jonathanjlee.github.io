package com.sg.SuperHeroSightings.controllers;

import java.util.List;

import com.sg.SuperHeroSightings.dao.SightingDao;
import com.sg.SuperHeroSightings.entities.Sighting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("/")
    public String displayLast10Sightings(Model model) {
        List<Sighting> sightings = sightingDao.getLast10Sightings();
        model.addAttribute("sightings", sightings);
        return "index";
    }
}
