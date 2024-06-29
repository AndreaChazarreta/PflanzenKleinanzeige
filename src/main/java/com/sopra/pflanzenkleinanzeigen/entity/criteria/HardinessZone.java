package com.sopra.pflanzenkleinanzeigen.entity.criteria;

public enum HardinessZone {
    ZONE_1("< -45.6°C (< -50°F)"),
    ZONE_2("-45.6°C bis -40.0°C (-50°F bis -40°F)"),
    ZONE_3("-40.0°C bis -34.4°C (-40°F bis -30°F)"),
    ZONE_4("-34.4°C bis -28.9°C (-30°F bis -20°F)"),
    ZONE_5("-28.9°C bis -23.3°C (-20°F bis -10°F)"),
    ZONE_6("-23.3°C bis -17.8°C (-10°F bis 0°F)"),
    ZONE_7("-17.8°C bis -12.2°C (0°F bis 10°F)"),
    ZONE_8("-12.2°C bis -6.7°C (10°F bis 20°F)"),
    ZONE_9("-6.7°C bis -1.1°C (20°F bis 30°F)"),
    ZONE_10("-1.1°C bis 4.4°C (30°F bis 40°F)"),
    ZONE_11("4.4°C bis 10.0°C (40°F bis 50°F)"),
    ZONE_12("10.0°C bis 15.6°C (50°F bis 60°F)"),
    ZONE_13("15.6°C bis 21.1°C (60°F bis 70°F)");

    private final String description;

    HardinessZone(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
