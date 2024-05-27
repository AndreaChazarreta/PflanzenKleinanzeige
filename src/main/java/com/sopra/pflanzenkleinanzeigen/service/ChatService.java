package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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


    /**
     * This method lists all chats from one specific user
     * @param userId of one user
     */
    public List<Chat> findUserChats(int userId) {
        return chatRepository.findChatsByUserId(userId);
    }
}