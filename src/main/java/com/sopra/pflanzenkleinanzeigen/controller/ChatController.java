package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ChatController class handles web requests related to chat operations.
 * It interacts with the ChatService to manage chat information, including
 * retrieving, creating, and deleting chats, and preparing the data for display in the view Layer.
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    /**
     * This method shows the chats for one user
     * @param model The model that is sent to the view.
     * @return "chats", the view with all chats from one specific user.
     */
    @GetMapping("/chats")
    public String getChats (Model model){
        Benutzer currentBenutzer = userService.getCurrentUser();
        model.addAttribute("allChats", chatService.findUserChats(currentBenutzer.getUserId()));
        return "chats";
    }

    /**
     * This method shows the messages in a specific chat.
     * @param chatId The ID of the chat whose messages should be displayed.
     * @param model The model that is sent to the view.
     * @return "messages", the view with all messages in a specific chat.
     */
    @GetMapping("/chats/{chatId}")
    public String getChat(@PathVariable int chatId, Model model) {
        try {
            Chat chat = chatService.findChatById(chatId);
            Benutzer seller = chat.getPlant().getSeller();
            Benutzer possibleBuyer = chat.getPossibleBuyer();
            Benutzer currentUser = userService.getCurrentUser();
            if(! (currentUser.equals(possibleBuyer) || currentUser.equals(seller))){
                logger.error("Benutzer ist nicht berechtigt, die Nachrichten in diesen Chat zu sehen.");
                model.addAttribute("error", "Sie sind nicht berechtigt, die Nachrichten in diesen Chat zu sehen, da sie nicht Teilnehmer dieses Chats sind.");
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
