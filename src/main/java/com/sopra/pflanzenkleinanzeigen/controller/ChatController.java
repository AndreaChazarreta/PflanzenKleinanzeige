package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * This method shows the chats for one user
     * @param userId
     * @param model
     * @return chats, the view with all chats from one specific user
     */
    @GetMapping("/chats/{userId}")
    public String getChats (@PathVariable int userId, Model model){
        model.addAttribute("allChats", chatService.findUserChats(userId));
        return "chats";
    }


}
