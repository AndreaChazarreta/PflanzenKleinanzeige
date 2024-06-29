package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum WaterRequirement {
    LOW("Gering"),
    MEDIUM("Mittel"),
    HIGH("Hoch");

    private final String description;

    WaterRequirement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
