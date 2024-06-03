package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a chat in the system.
 * It contains information about the chat such as the possible buyer, the plant, and the messages in the chat.
 */
@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Integer chatId;

    @ManyToOne
    @JoinColumn (name = "possibleBuyerId")
    private Benutzer possibleBuyer;

    @ManyToOne
    @JoinColumn (name = "plantId")
    private Plant plant;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    /**
     * Default constructor for Hibernate.
     */
    public Chat() {
        // empty constructor for Hibernate
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Benutzer getPossibleBuyer() {
        return possibleBuyer;
    }

    public void setPossibleBuyer(Benutzer possibleBuyer) {
        this.possibleBuyer = possibleBuyer;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}