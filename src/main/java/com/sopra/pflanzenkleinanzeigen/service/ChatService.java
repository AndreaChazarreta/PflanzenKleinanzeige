package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a chat service in the system.
 * It contains methods to save a chat and to find a chat by id.
 */
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat findChatById(Integer id) {
        return chatRepository.findById(id).orElse(null);
    }


    /**
     * This method lists all chats from one specific user
     * @param userId of one user
     */
    public List<Chat> findUserChats(int userId) {
        try {
            List<Chat> allChats = chatRepository.findAll();
            List<Chat> userChats = new ArrayList<>();
            for (Chat chat : allChats) {
                int sellerId = chat.getPlant().getSeller().getUserId();
                int buyerId = chat.getPossibleBuyer().getUserId();
                if (sellerId == userId || buyerId == userId) {
                    userChats.add(chat);
                }
            }
            return userChats;
            //TODO: Rückgabe einer leeren Liste im Fehlerfall oder Verlinkung auf eine Fehlerseite?
        } catch (Exception e) {
            logger.error("Fehler beim Abrufen der Chats für Benutzer mit der Id: " + userId, e);
            return Collections.emptyList();
        }
    }
}