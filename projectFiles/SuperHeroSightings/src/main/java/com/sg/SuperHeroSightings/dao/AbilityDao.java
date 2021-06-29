/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Ability;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface AbilityDao {

    /**
     * Pulls an Ability using a given abilityId.
     *
     * @param abilityId
     * @return The affiliated Ability object.
     */
    Ability getAbilityById(int abilityId);

    /**
     * Pulls a list of all Abilities in the database.
     *
     * @return A list of Ability objects.
     */
    List<Ability> getAllAbilities();

    /**
     * Adds an Ability to the database.
     *
     * @param ability
     * @return The added Ability object.
     */
    Ability addAbility(Ability ability);

    /**
     * Updates an existing Ability in the database.
     *
     * @param ability
     */
    void updateAbility(Ability ability);

    /**
     * Deletes an Ability using a given abilityId.
     *
     * @param abilityId
     */
    void deleteAbilityById(int abilityId);
}
