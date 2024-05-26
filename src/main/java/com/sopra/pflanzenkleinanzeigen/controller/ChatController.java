package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller is responsible for managing chats.
 * It provides endpoints for retrieving chats for a specific user and for retrieving messages in a specific chat.
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    /**
     * This method shows the chats for one user
     * @param userId The ID of the user whose chats should be displayed.
     * @param model The model that is sent to the view.
     * @return "chats", the view with all chats from one specific user.
     */
    @GetMapping("/chats/{userId}")
    public String getChats(@PathVariable int userId, Model model) {
        try {
            model.addAttribute("allChats", chatService.findUserChats(userId));
            return "chats";
        } catch (Exception findAllChatsException) {
            logger.error("Fehler beim Abrufen der Chats für Benutzer mit Id: " + userId, findAllChatsException);
            model.addAttribute("error", "Fehler beim Abrufen der Chats für Benutzer mit Id: " + userId);
            return "error";
        }
    }

    /**
     * This method shows the messages in a specific chat.
     * @param chatId The ID of the chat whose messages should be displayed.
     * @param model The model that is sent to the view.
     * @return "messages", the view with all messages in a specific chat.
     */
    @GetMapping("/chat/{chatId}")
    public String getChat(@PathVariable int chatId, Model model) {
        try {
            Chat chat = chatService.findChatById(chatId);
            if (chat == null) {
                model.addAttribute("error", "Chat mit Id: " + chatId + " not found.");
                return "error";
            }
            model.addAttribute("allMessages", chat.getMessages());
            return "messages";
        } catch (Exception findChatException) {
            logger.error("Fehler beim Abrufen der Nachrichten für Chat mit Id: " + chatId, findChatException);
            model.addAttribute("error", "Fehler beim Abrufen der Nachrichten für Chat mit Id: " + chatId);
            return "error";
        }
    }
}
