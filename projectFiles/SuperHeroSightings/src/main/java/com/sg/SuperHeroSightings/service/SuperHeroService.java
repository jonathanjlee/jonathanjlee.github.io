package com.sg.SuperHeroSightings.service;

import java.time.LocalDate;
import java.util.List;

import com.sg.SuperHeroSightings.entities.Ability;
import com.sg.SuperHeroSightings.entities.Address;
import com.sg.SuperHeroSightings.entities.Location;
import com.sg.SuperHeroSightings.entities.Org;
import com.sg.SuperHeroSightings.entities.Phone;
import com.sg.SuperHeroSightings.entities.PhoneType;
import com.sg.SuperHeroSightings.entities.Sighting;
import com.sg.SuperHeroSightings.entities.Supe;

public interface SuperHeroService {

    // items for Supes
    List<Org> getOrgsForSupe(Supe supe);

    void getAbilitiesForSupes(List<Supe> supes);

    // void getOrgsForSupes(List<Supe> supes);

    List<Ability> getAbilitiesForSupe(Supe supe);

    // items for Orgs
    Address getAddressForOrg(Org org);

    Phone getPhoneForOrg(Org org);

    void getInfoForOrgs(List<Org> orgs);

    List<Supe> getMembersForOrg(Org org);

    // items for Phones
    PhoneType getPhoneTypeForPhone(Phone phone);

    void addPhoneTypesToPhones(List<Phone> phones);

    // items for Locations
    Address getAddressForLocation(Location location);

    List<Sighting> getSightingsByDate(LocalDate date);

    void getInfoForSightings(List<Sighting> sightings);
}
