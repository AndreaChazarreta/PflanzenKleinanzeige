package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller is responsible for managing chats.
 * It provides endpoints for retrieving chats for a specific user and for retrieving messages in a specific chat.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    /**
     * This method shows the home page of your application.
     * @param model contains all ModelAttributes.
     * @return home page.
     */
    @GetMapping("/")
    public String showHome(Model model) {
        Benutzer currentBenutzer = userService.getCurrentUser();
        model.addAttribute("currentBenutzer", currentBenutzer);
        model.addAttribute("message", "Willkommen " + currentBenutzer.getUsername() + "!");
        return "home";
    }
}
