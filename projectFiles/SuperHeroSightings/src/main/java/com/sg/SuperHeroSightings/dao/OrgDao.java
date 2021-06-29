/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.dao;

import com.sg.SuperHeroSightings.entities.Org;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface OrgDao {

    /**
     * Pulls an Org using a given orgId.
     *
     * @param orgId
     * @return The affiliated Org object.
     */
    Org getOrgById(int orgId);

    /**
     * Pulls a list of all Orgs in the database.
     *
     * @return A list of Org objects.
     */
    List<Org> getAllOrgs();

    /**
     * Adds an Org to the Database.
     *
     * @param org
     * @return The added Org object.
     */
    Org addOrg(Org org);

    /**
     * Updates an existing Org in the database.
     *
     * @param org
     */
    void updateOrg(Org org);

    /**
     * Deletes an Org using a given orgId.
     *
     * @param orgId
     */
    void deleteOrgById(int orgId);

}
