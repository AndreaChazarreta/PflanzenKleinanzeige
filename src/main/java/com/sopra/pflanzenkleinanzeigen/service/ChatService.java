package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.repository.ChatRepository;
import com.sopra.pflanzenkleinanzeigen.repository.MessageRepository;
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

    @Autowired
    private MessageRepository messageRepository;

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat findChatById(Integer id) {
        return chatRepository.findById(id).orElse(null);
    }

    public List<Chat> findUserChats(int userId) {
        return chatRepository.findChatsByUserId(userId);
    }

    public List<Chat> findUserChatsWithUnreadCount(int userId) {
        List<Chat> chats = chatRepository.findChatsByUserId(userId);
        for (Chat chat : chats) {
            int unreadCount = messageRepository.countUnreadMessages(chat.getChatId(), userId);
            chat.setUnreadCount(unreadCount);
        }
        return chats;
    }
}