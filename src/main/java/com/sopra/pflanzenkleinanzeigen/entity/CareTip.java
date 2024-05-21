package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CareTip {

    @Id
    @GeneratedValue
    private Integer tipId;

    private String irrigation;

    private String lightingConditions;

    private String fertilization;

    private String repotting;

    private String temperature;

    private String otherTips;

    @OneToMany(mappedBy = "careTip")
    private List<Plant> describedPlants = new ArrayList<>();


    public CareTip() {
        // empty constructor for Hibernate
    }

    public Integer getTipId() {
        return tipId;
    }

    public void setTipId(Integer tipId) {
        this.tipId = tipId;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getLightingConditions() {
        return lightingConditions;
    }

    public void setLightingConditions(String lightingConditions) {
        this.lightingConditions = lightingConditions;
    }

    public String getFertilization() {
        return fertilization;
    }

    public void setFertilization(String fertilization) {
        this.fertilization = fertilization;
    }

    public String getRepotting() {
        return repotting;
    }

    public void setRepotting(String repotting) {
        this.repotting = repotting;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOtherTips() {
        return otherTips;
    }

    public void setOtherTips(String otherTips) {
        this.otherTips = otherTips;
    }
}