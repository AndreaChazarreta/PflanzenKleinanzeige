package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a plant in the system.
 * It contains information about the plant such as the name, price, height, description, whether a pot is included, an image,
 * the seller, the buyer, the care tip, and related entities like the wishlist, chats, and the seller.
 */
@Entity
public class Plant {

    @Id
    @GeneratedValue
    private Integer plantId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    // Price in Euro
    @DecimalMin(value = "0.0", message = "Der Preis muss größer gleich Null sein")
    @Digits(integer = 10, fraction = 2, message = "Preis muss ein gültiger Geldbetrag sein mit bis zu 2 Nachkommastellen")
    private BigDecimal price;

    // Height in centimeters
    @DecimalMin(value = "0.0", message = "Die Höhe muss größer gleich Null sein")
    @Digits(integer = 10, fraction = 2, message = "Höhe muss eine gültige Zahl mit bis zu 2 Nachkommastellen")
    private BigDecimal height;

    @NotBlank(message = "Beschreibung kann nicht leer sein")
    @Column(length = 5000)
    private String description;

    private boolean potIncluded;

    private String imagePath;

    private boolean sold;

    private boolean seed;

    @ManyToMany(targetEntity = com.sopra.pflanzenkleinanzeigen.entity.Benutzer.class, fetch = FetchType.EAGER)
    private Set<Benutzer> wishedBy = new HashSet<>();

    @ManyToOne
    @JoinColumn (name = "sellerId")
    private Benutzer seller;

    @ManyToOne
    @JoinColumn (name = "buyerId")
    private Benutzer buyer;

    private boolean adIsActive = true;

    @ManyToOne
    @JoinColumn (name = "careTipId")
    private CareTip careTip;

    @OneToMany(mappedBy = "plant")
    private List<Chat> chatsAboutThisPlant = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "categoryId")
    private Category category;

    private String lifespan;

    private String floweringTime;

    private boolean isToxicForPets;

    private boolean isAirPurifying;

    private String usability;

    private String color;

    private String leafShape;

    private String growthRate;

    private String standort;

    private String plantFamily;

    private String fruits;

    /**
     * Default constructor for Hibernate.
     */
    public Plant() {
        // empty constructor for Hibernate
    }

    public Plant(String name, BigDecimal price, BigDecimal height, String description){
        this.name = name;
        this.price = price;
        this.height = height;
        this.description = description;
    }

    public Integer getPlantId() {
        return plantId;
    }

    public void setPlantId(Integer plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPotIncluded() {
        return potIncluded;
    }

    public void setPotIncluded(boolean potIncluded) {
        this.potIncluded = potIncluded;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<Benutzer> getWishedBy() {
        return wishedBy;
    }

    public void setWishedBy(Set<Benutzer> wishedBy) {
        this.wishedBy = wishedBy;
    }

    public Benutzer getSeller() {
        return seller;
    }

    public void setSeller(Benutzer seller) {
        this.seller = seller;
    }

    public Benutzer getBuyer() {
        return buyer;
    }

    public void setBuyer(Benutzer buyer) {
        this.buyer = buyer;
        if (buyer != null) {
            this.adIsActive = false;
        }
    }

    public boolean isAdIsActive() {
        return adIsActive;
    }

    public void setAdIsActive(boolean adIsActive) {
        this.adIsActive = adIsActive;
    }

    public CareTip getCareTip() {
        return careTip;
    }

    public void setCareTip(CareTip careTip) {
        this.careTip = careTip;
    }

    public List<Chat> getChatsAboutThisPlant() {
        return chatsAboutThisPlant;
    }

    public void setChatsAboutThisPlant(List<Chat> chatsAboutThisPlant) {
        this.chatsAboutThisPlant = chatsAboutThisPlant;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isSeed() {
        return seed;
    }

    public void setSeed(boolean seed) {
        this.seed = seed;
    }

    public String isLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    public boolean isToxicForPets() {
        return isToxicForPets;
    }

    public void setToxicForPets(boolean toxicForPets) {
        isToxicForPets = toxicForPets;
    }

    public boolean isAirPurifying() {
        return isAirPurifying;
    }

    public void setAirPurifying(boolean airPurifying) {
        isAirPurifying = airPurifying;
    }

    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLeafShape() {
        return leafShape;
    }

    public void setLeafShape(String leafShape) {
        this.leafShape = leafShape;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public String getPlantFamily() {
        return plantFamily;
    }

    public void setPlantFamily(String plantFamily) {
        this.plantFamily = plantFamily;
    }

    public String getFruits() {
        return fruits;
    }

    public void setFruits(String fruits) {
        this.fruits = fruits;
    }
}
