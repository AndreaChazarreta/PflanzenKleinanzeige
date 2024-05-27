package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class represents a plant in the system.
 * It contains information about the plant such as the name, price, height, description, whether a pot is included, an image, the seller, the buyer, the care tip, and related entities like the wishlist, chats, and the seller.
 */
@Entity
public class Plant {

    @Id
    @GeneratedValue
    private Integer plantId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    // Price in Euro
    @DecimalMin(value = "0.0", inclusive = true, message = "Der Preis muss größer gleich Null sein")
    @Digits(integer = 10, fraction = 2, message = "Preis muss ein gültiger Geldbetrag sein mit bis zu 2 Nachkommastellen")
    private BigDecimal price;

    // Height in centimeters
    @DecimalMin(value = "0.0", inclusive = true, message = "Die Höhe muss größer gleich Null sein")
    @Digits(integer = 10, fraction = 2, message = "Höhe muss eine gültige Zahl mit bis zu 2 Nachkommastellen")
    private BigDecimal height;

    @NotBlank(message = "Beschreibung kann nicht leer sein")
    private String description;

    private boolean potIncluded;

    private String image;

    @ManyToMany(targetEntity = com.sopra.pflanzenkleinanzeigen.entity.Benutzer.class, fetch = FetchType.EAGER)
    private Set<Benutzer> benutzerWishlist;

    @ManyToOne
    @JoinColumn (name = "sellerId")
    private Benutzer seller;

    @ManyToOne
    @JoinColumn (name = "buyerId")
    private Benutzer buyer;

    @ManyToOne
    @JoinColumn (name = "careTipId")
    private CareTip careTip;

    //TODO: Cascade schauen, passt so?
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chatsAboutThisPlant = new ArrayList<>();

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Benutzer> getBenutzerWishlist() {
        return benutzerWishlist;
    }

    public void setBenutzerWishlist(Set<Benutzer> benutzerWishlist) {
        this.benutzerWishlist = benutzerWishlist;
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
}
