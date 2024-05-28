package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * This class represents a role in the system. It is used to control the authorities of the users.
 * It contains information about the role such as the role name.
 */
@Entity
public class Rolle {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;

    /**
     * Default constructor for Hibernate.
     */
    public Rolle() {
        //empty constructor for Hibernate
    }

    public Rolle(String rolename) {
        this.rolename = rolename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
