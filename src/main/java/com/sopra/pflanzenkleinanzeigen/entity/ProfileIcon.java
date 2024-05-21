package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProfileIcon {

    @Id
    @GeneratedValue
    private Integer iconId;

    private String iconImage;

    @OneToMany(mappedBy = "profileIcon")
    private List<Benutzer> selectedIcon = new ArrayList<>();


    public ProfileIcon() {
        // empty constructor for Hibernate
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public List<Benutzer> getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(List<Benutzer> selectedIcon) {
        this.selectedIcon = selectedIcon;
    }
}