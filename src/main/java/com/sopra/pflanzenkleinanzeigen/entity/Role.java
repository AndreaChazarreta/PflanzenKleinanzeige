package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;

    public Role() {
        //empty constructor for Hibernate
    }

    public Role(String rolename) {
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
