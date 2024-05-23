package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.MessageService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    /**
     * This method shows the chats for one user
     * @param userId
     * @param model
     * @return chats, the view with all chats from one specific user
     */
    @GetMapping("/chats/{userId}")
    public String getChats (@PathVariable int userId, Model model){
        Benutzer currentBenutzer = userService.getCurrentUser();
        if (userId != currentBenutzer.getUserId()) {
            userId = currentBenutzer.getUserId();
            return "redirect:/chats/" + userId;
        }
        model.addAttribute("allChats", chatService.findUserChats(userId));
        return "chats";
    }

    @GetMapping("/chat/{chatId}")
    public String getChat (@PathVariable int chatId, Model model){
        Chat chat = chatService.findChatById(chatId);
        model.addAttribute("allMessages", chat.getMessages());
        return "messages";
    }

}
