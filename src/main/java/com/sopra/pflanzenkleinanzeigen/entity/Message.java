package com.sopra.pflanzenkleinanzeigen.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Integer messageId;

    @ManyToOne
    @JoinColumn (name = "senderId")
    private Benutzer sender;


    private String messageContent;

    private Instant sentAt;

    @ManyToOne
    @JoinColumn (name = "chatId")
    private Chat chat;

    public Message() {
        // empty constructor for Hibernate
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Benutzer getSender() {
        return sender;
    }

    public void setSender(Benutzer sender) {
        this.sender = sender;
    }
}