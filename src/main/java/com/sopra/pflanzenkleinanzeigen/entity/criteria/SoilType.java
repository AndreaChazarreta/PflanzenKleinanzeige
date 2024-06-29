package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum SoilType {
    SANDY("Sandig"),
    LOAMY("Lehmig"),
    CLAY("Tonig"),
    SILTY("Schluffig"),
    PEATY("Torfig"),
    CHALKY("Kalkhaltig"),
    WELL_DRAINED("Gut durchlässig"),
    MOIST("Feucht");

    private final String description;

    SoilType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
