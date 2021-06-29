package com.sg.SuperHeroSightings.controllers;

import java.util.ArrayList;
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
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Phone;
import com.sg.SuperHeroSightings.entities.PhoneType;
import com.sg.SuperHeroSightings.entities.Supe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrgsController {

    Set<ConstraintViolation<Phone>> phoneViolations = new HashSet<>();
    Set<ConstraintViolation<Address>> addressViolations = new HashSet<>();
    Set<ConstraintViolation<List<Supe>>> supeViolations = new HashSet<>();
    Set<ConstraintViolation<Org>> orgViolations = new HashSet<>();

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

    @GetMapping("orgs")
    public String displayOrgs(Model model) {
        List<Org> orgs = orgDao.getAllOrgs();
        List<Address> addresses = addressDao.getAllAddresses();
        List<Phone> phones = phoneDao.getAllPhones();
        List<PhoneType> phoneTypes = phoneTypeDao.getAllPhoneTypes();
        List<Supe> supes = supeDao.getAllSupes();
        model.addAttribute("orgs", orgs);
        model.addAttribute("addresses", addresses);
        model.addAttribute("phones", phones);
        model.addAttribute("phoneTypes", phoneTypes);
        model.addAttribute("supes", supes);
        model.addAttribute("phoneErrors", phoneViolations);
        model.addAttribute("addressErrors", addressViolations);
        model.addAttribute("supeErrors", supeViolations);
        model.addAttribute("orgErrors", orgViolations);
        return "orgs";
    }

    @PostMapping("addOrg")
    public String addOrg(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String coordinates = request.getParameter("latitude") + ", " + request.getParameter("longitude");
        String phoneNumber = request.getParameter("phoneNumber");
        String phoneTypeId = request.getParameter("phoneTypeId");
        String[] supeIds = request.getParameterValues("supeId");

        PhoneType phoneType = phoneTypeDao.getPhoneTypeById(Integer.parseInt(phoneTypeId));

        Phone phone = new Phone();
        phone.setPhoneNumber(phoneNumber);
        phone.setPhoneType(phoneType);

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        address.setCoordinates(coordinates);

        List<Supe> members = new ArrayList<>();
        if(supeIds!=null && supeIds.length > 0) {
            for (String supeId : supeIds) {
                members.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            }
        }

        Org org = new Org();
        org.setName(name);
        org.setDescription(description);
        org.setAddress(address);
        org.setPhone(phone);
        org.setMembers(members);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        phoneViolations = validate.validate(phone);
        addressViolations = validate.validate(address);
        supeViolations = validate.validate(members);
        orgViolations = validate.validate(org);

        if(phoneViolations.isEmpty() && addressViolations.isEmpty() && supeViolations.isEmpty() && orgViolations.isEmpty()) {
            phoneDao.addPhone(phone);
            addressDao.addAddress(address);
            orgDao.addOrg(org);
        }

        return "redirect:/orgs";
    }

    @GetMapping("deleteOrg")
    public String deleteOrg(Integer id) {
        orgDao.deleteOrgById(id);

        return "redirect:/orgs";
    }

    @GetMapping("editOrg")
    public String editOrg(Integer id, Model model) {
        Org org = orgDao.getOrgById(id);
        Address address = org.getAddress();
        Phone phone = org.getPhone();
        List<PhoneType> phoneTypes = phoneTypeDao.getAllPhoneTypes();
        List<Supe> supes = supeDao.getAllSupes();

        model.addAttribute("org", org);
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);
        model.addAttribute("phoneTypes", phoneTypes);
        model.addAttribute("supes", supes);

        return "editOrg";
    }

    @PostMapping("editOrg")
    public String performEditOrg(@Valid Org org, BindingResult orgResult, 
    @Valid Address address, BindingResult addressResult, 
    @Valid Phone phone, BindingResult phoneResult,  
    HttpServletRequest request, Model model) {
        List<PhoneType> phoneTypes = phoneTypeDao.getAllPhoneTypes();
        int phoneTypeId = Integer.parseInt(request.getParameter("phoneTypeId"));
        String[] supeIds = request.getParameterValues("supeId");

        org.setAddress(address);
        phone.setPhoneType(phoneTypeDao.getPhoneTypeById(phoneTypeId));
        org.setPhone(phone);

        List<Supe> members = new ArrayList<>();
        if(supeIds != null) {
            for(String supeId : supeIds) {
                members.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            }
        } else {
            FieldError error = new FieldError("org", "members", "The team must include at least one member.");
            orgResult.addError(error);
        }

        org.setMembers(members);

        if(orgResult.hasErrors() || addressResult.hasErrors() || phoneResult.hasErrors()) {
            model.addAttribute("orgs", orgDao.getAllOrgs());
            model.addAttribute("addresses", addressDao.getAllAddresses());
            model.addAttribute("phones", phoneDao.getAllPhones());
            model.addAttribute("phoneTypes", phoneTypes);
            model.addAttribute("supes", supeDao.getAllSupes());
            return "editOrg";
        }

        orgDao.updateOrg(org);

        return "redirect:/orgs";
    }
}
