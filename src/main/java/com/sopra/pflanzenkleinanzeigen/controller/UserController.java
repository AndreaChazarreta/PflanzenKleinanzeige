package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Zeigt die Pflanzen Seite mit allen Pflanzen an.
     * @return die View mit allen Pflanzen (also die plants.html).
     */
    @GetMapping("/users")
    public String getusers(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/users/{name}")
    public String getUserDetails(@PathVariable String name, Model model) {
        Benutzer user = userService.getUserByUsername(name);
        if (user == null) {
            // Handle case where plant is not found, maybe redirect to an error page or back to the plants list
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "userDetails";
    }

}