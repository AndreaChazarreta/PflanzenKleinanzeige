package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum Usability {
    MEDICINAL("Medizinisch"),
    CULINARY("Kulinarisch"),
    ORNAMENTAL("Zierpflanze"),
    OTHER("Andere");

    private final String description;

    Usability(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
