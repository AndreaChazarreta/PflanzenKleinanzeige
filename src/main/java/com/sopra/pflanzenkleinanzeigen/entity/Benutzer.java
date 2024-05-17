package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Benutzer {

    @Id
    @GeneratedValue
    private Integer userId;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rolle> roles;


    public Benutzer() {
        // empty constructor for Hibernate
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Rolle> getRoles() {
        return roles;
    }

    //TODO: FRAGEN!! Hier stand "return rollen" --> public Set<Role> getRoles() { return roles; }

    public void setRoles(Set<Rolle> roles) {
        this.roles = roles;
    }
}