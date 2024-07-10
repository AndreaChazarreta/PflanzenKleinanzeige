package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a care tip in the system.
 * It contains information about the care tip such as irrigation, lighting conditions, fertilization, repotting, temperature, and other tips.
 * It also contains a list of plants that this care tip describes.
 */
@Entity
public class CareTip {

    @Id
    @GeneratedValue
    private Integer tipId;

    private String plantName;

    private String irrigation;

    private String lightingConditions;

    private String fertilization;

    private String repotting;

    private String temperature;

    private String otherTips;

    private String planting;

    @OneToMany(mappedBy = "careTip")
    private List<Plant> describedPlants = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "categoryId")
    private Category category;


    public CareTip() {
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

    public List<Plant> getDescribedPlants() {
        return describedPlants;
    }

    public void setDescribedPlants(List<Plant> describedPlants) {
        this.describedPlants = describedPlants;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPlanting() {
        return planting;
    }

    public void setPlanting(String planting) {
        this.planting = planting;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}