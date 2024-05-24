package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class represents a user in the system.
 * It contains information about the user such as their username, password, roles, and related entities like plants and chats.
 */
@Entity
public class Benutzer {

    @Id
    @GeneratedValue
    private Integer userId;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean enabled = true;

    private String firstname;

    private String lastname;

    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rolle> roles;

    @OneToMany(mappedBy = "seller")
    private List<Plant> uploadedPlants = new ArrayList<>();

    @OneToMany(mappedBy = "buyer")
    private List<Plant> purchasedPlants = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "selectedIconId")
    private ProfileIcon profileIcon;

    @OneToMany(mappedBy = "possibleBuyer")
    private List<Chat> startedChats = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sendedMessages = new ArrayList<>();

    /**
     * Default constructor for Hibernate.
     */
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

    public void setRoles(Set<Rolle> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public List<Plant> getUploadedPlants() {
        return uploadedPlants;
    }

    public void setUploadedPlants(List<Plant> uploadedPlants) {
        this.uploadedPlants = uploadedPlants;
    }

    public List<Plant> getPurchasedPlants() {
        return purchasedPlants;
    }

    public void setPurchasedPlants(List<Plant> purchasedPlants) {
        this.purchasedPlants = purchasedPlants;
    }

    public ProfileIcon getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(ProfileIcon profileIcon) {
        this.profileIcon = profileIcon;
    }

    public List<Chat> getStartedChats() {
        return startedChats;
    }

    public void setStartedChats(List<Chat> startedChats) {
        this.startedChats = startedChats;
    }
}
