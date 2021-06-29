/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.entities;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author jonat
 */
public class Supe {

    private int supeId;

    @NotBlank(message = "'Supe name' must not be blank.")
    @Size(max = 30, message = "'Supe name' must be less than 30 characters.")
    private String name;
    
    @Size(max = 255, message = "'Description' must be less than 255 characters.")
    private String description;
    
    @NotEmpty(message = "'Abilities' cannot be empty.")
    // Think about putting a maxSelection annotation here
    private List<Ability> abilities;

    public int getSupeId() {
        return supeId;
    }

    public void setSupeId(int supeId) {
        this.supeId = supeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.supeId;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.abilities);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supe other = (Supe) obj;
        if (this.supeId != other.supeId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.abilities, other.abilities)) {
            return false;
        }
        return true;
    }

}
