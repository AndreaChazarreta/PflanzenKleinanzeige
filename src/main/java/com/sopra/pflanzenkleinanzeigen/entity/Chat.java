package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Integer chatId;

    @ManyToOne
    @JoinColumn (name = "benutzerId")
    private Benutzer benutzer;

    @ManyToOne
    @JoinColumn (name = "plantId")
    private Plant plant;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();


    public Chat() {
        // empty constructor for Hibernate
    }

}