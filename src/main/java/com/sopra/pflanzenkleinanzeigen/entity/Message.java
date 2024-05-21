package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Integer messageId;

    private int senderId;
//TODO: gucken ob das so passt

    private String messageContent;

    private Instant sentAt;

    @ManyToOne
    @JoinColumn (name = "chat")
    private Chat chat;

    public Message() {
        // empty constructor for Hibernate
    }

}