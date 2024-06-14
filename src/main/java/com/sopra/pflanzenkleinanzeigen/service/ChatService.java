package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * This class encapsulates access to the ChatRepository. It provides methods for managing Chat entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat findChatById(Integer id) {
        return chatRepository.findById(id).orElse(null);
    }

    public List<Chat> findUserChats(int userId) {
        return chatRepository.findChatsByUserId(userId);
    }
}