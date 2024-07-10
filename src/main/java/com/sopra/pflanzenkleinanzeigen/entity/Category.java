
package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a profile icon in the system.
 * It contains information about the profile icon such as the image and the users who selected this icon.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Integer categoryId;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Plant> plantCategory = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<CareTip> category = new ArrayList<>();

    /**
     * Default constructor for Hibernate.
     */
    public Category() {
        // empty constructor for Hibernate
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Plant> getPlantCategory() {
        return plantCategory;
    }

    public void setPlantCategory(List<Plant> plantCategory) {
        this.plantCategory = plantCategory;
    }

    public List<CareTip> getCategory() {
        return category;
    }

    public void setCategory(List<CareTip> category) {
        this.category = category;
    }
}