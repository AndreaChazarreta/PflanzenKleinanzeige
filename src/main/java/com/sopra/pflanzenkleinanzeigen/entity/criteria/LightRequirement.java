package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum LightRequirement {
    FULL_SUN("Volle Sonne"),
    PARTIAL_SHADE("Halbschatten"),
    FULL_SHADE("Vollschatten");

    private final String description;

    LightRequirement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
