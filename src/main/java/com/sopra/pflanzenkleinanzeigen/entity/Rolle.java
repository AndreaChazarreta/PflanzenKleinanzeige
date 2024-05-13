package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Rolle {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;

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
