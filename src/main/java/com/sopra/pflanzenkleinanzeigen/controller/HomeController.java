package com.sopra.pflanzenkleinanzeigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    /**
     * Zeigt die Startseite Ihrer Anwendung.
     * @param model enthält alle ModelAttribute.
     * @return home-Seite.
     */
    @GetMapping("/")
    public String showHome(Model model) {
        String username = userService.getCurrentUser().getUsername();
        model.addAttribute("message", "Willkommen " + username + "!");
        return "home";
    }
}
