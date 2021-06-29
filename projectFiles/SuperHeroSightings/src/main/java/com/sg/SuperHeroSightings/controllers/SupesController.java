package com.sg.SuperHeroSightings.controllers;

import java.util.ArrayList;
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
import com.sg.SuperHeroSightings.entities.Ability;
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Supe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupesController {

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

    @GetMapping("supes")
    public String displaySupes(Model model) {
        List<Supe> supes = supeDao.getAllSupes();
        List<Ability> abilities = abilityDao.getAllAbilities();
        List<Org> orgs = orgDao.getAllOrgs();
        model.addAttribute("supes", supes);
        model.addAttribute("abilities", abilities);
        model.addAttribute("orgs", orgs);
        return "supes";
    }

    @PostMapping("addSupe")
    public String addSupe(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] abilityIds = request.getParameterValues("abilityId");
        String[] orgIds = request.getParameterValues("orgId");

        List<Ability> abilities = new ArrayList<>();
        for (String abilityId : abilityIds) {
            abilities.add(abilityDao.getAbilityById(Integer.parseInt(abilityId)));
        }

        List<Org> teams = new ArrayList<>();
        for (String orgId : orgIds) {
            teams.add(orgDao.getOrgById(Integer.parseInt(orgId)));
        }

        Supe supe = new Supe();
        supe.setName(name);
        supe.setDescription(description);
        supe.setAbilities(abilities);
        supeDao.addSupe(supe);

        return "redirect:/supes";
    }

    @GetMapping("deleteSupe")
    public String deleteSupe(Integer id) {
        supeDao.deleteSupeById(id);

        return "redirect:/supes";
    }

    @GetMapping("editSupe")
    public String editSupe(HttpServletRequest request, Model model) {
        int supeId = Integer.parseInt(request.getParameter("id"));
        Supe supe = supeDao.getSupeById(supeId);
        List<Ability> abilities = abilityDao.getAllAbilities();

        model.addAttribute("supe", supe);
        model.addAttribute("abilities", abilities);

        return "editSupe";
    }

    @PostMapping("editSupe")
    public String performEditSupe(HttpServletRequest request) {
        int supeId = Integer.parseInt(request.getParameter("supeId"));
        Supe supe = supeDao.getSupeById(supeId);

        String[] abilityIds = request.getParameterValues("abilityId");

        List<Ability> abilities = new ArrayList<>();
        for (String abilityId : abilityIds) {
            abilities.add(abilityDao.getAbilityById(Integer.parseInt(abilityId)));
        }

        supe.setName(request.getParameter("name"));
        supe.setDescription(request.getParameter("description"));
        supe.setAbilities(abilities);
        supeDao.updateSupe(supe);

        return "redirect:/supes";
    }

}
