package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum FloweringTime {
        SPRING("Frühling"),
        SUMMER("Sommer"),
        AUTUMN("Herbst"),
        WINTER("Winter"),
        YEAR_ROUND("Ganzjährig");

    private final String description;

    FloweringTime(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
