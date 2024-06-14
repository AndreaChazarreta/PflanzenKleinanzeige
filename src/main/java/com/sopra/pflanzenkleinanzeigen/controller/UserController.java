package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The UserController class handles web requests related to user operations.
 * It interacts with the UserService to manage user information and preparing the
 * data for display in the view Layer.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlantService plantService;

    //TODO: der User soll nur sein Profil ansehen können, aber nicht den Profil von andere Leute!
    @GetMapping("/users")
    public String getUserDetails(Model model) {
        Benutzer user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/myPlantsForSale")
    public String myPlantsForSaleOverview(@RequestParam(required = false) String name, Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        List<Plant> plants;
        if (name != null && !name.isEmpty()) {
            plants = userService.findPlantsByNameAndSeller(name, currentUser.getUserId());
        } else {
            plants = userService.findActivePlantsBySeller(currentUser.getUserId());
        }
        model.addAttribute("userPlants", plants);
        return "myPlantsForSale";
    }

    @GetMapping("/myPlants")
    public String myPlantsOverview(@RequestParam(required = false) String name, Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        List<Plant> boughtPlants;
        if (name != null && !name.isEmpty()) {
            boughtPlants = userService.findPurchasedPlantsByNameAndBuyer(name, currentUser.getUserId());
        } else {
            boughtPlants = currentUser.getPurchasedPlants();
        }
        model.addAttribute("boughtPlants", boughtPlants);
        return "myPlants";
    }
}