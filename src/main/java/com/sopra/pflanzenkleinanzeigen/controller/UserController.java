package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The UserController class handles web requests related to user operations.
 * It interacts with the UserService to manage user information and preparing the
 * data for display in the view Layer.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //TODO: der User soll nur sein Profil ansehen können, aber nicht den Profil von andere Leute!
    @GetMapping("/users/{name}")
    public String getUserDetails(@PathVariable String name, Model model) {
        Benutzer user = userService.getUserByUsername(name);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "userDetails";
    }

}