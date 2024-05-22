package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Plant {

    @Id
    @GeneratedValue
    private Integer plantId;

    private String name;

    private float price;

    private float height;

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

    @OneToMany(mappedBy = "plant")
    private List<Chat> chatsAboutThisPlant = new ArrayList<>();

    public Plant() {
        // empty constructor for Hibernate
    }
    public Plant(String name, float price, float height, String description){
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
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
