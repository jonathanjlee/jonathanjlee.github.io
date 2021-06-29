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
import com.sg.SuperHeroSightings.entities.Ability;

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
public class AbilitiesController {

    Set<ConstraintViolation<Ability>> violations = new HashSet<>();

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

    @GetMapping("abilities")
    public String displayAbilities(Model model) {
        List<Ability> abilities = abilityDao.getAllAbilities();
        model.addAttribute("abilities", abilities);
        model.addAttribute("errors", violations);
        return "abilities";
    }

    @PostMapping("addAbility")
    public String addAbility(HttpServletRequest request) {
        String name = request.getParameter("name");

        Ability ability = new Ability();
        ability.setName(name);
    
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(ability);

        if(violations.isEmpty()) {
            abilityDao.addAbility(ability);
        }

        return "redirect:/abilities";
    }

    @GetMapping("deleteAbility")
    public String deleteAbility(Integer id) {
        abilityDao.deleteAbilityById(id);

        return "redirect:/abilities";
    }

    @GetMapping("editAbility")
    public String editAbility(Integer id, Model model) {
        Ability ability = abilityDao.getAbilityById(id);
        model.addAttribute("ability", ability);
        return "editAbility";
    }

    @PostMapping("editAbility")
    public String performEditAbility(@Valid Ability ability, BindingResult result) {
        if(result.hasErrors()) {
            return "editAbility";
        }
        abilityDao.updateAbility(ability);
        return "redirect:/abilities";
    }
}
