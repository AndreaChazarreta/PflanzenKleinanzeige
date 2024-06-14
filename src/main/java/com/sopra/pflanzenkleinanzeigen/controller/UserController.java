package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    /**
     * This method retrieves the current user and displays the user details on the profile page.
     * @param model The model that is sent to the view.
     * @return "profile", the view with the user details.
     */
    @GetMapping("/users")
    public String getUserDetails(Model model) {
        Benutzer user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    /**
     * This method retrieves all plants of the current user and displays them on the myPlantsOverview page.
     * @param model The model that is sent to the view.
     * @return "myPlantsOverview", the view with all plants of the current user.
     */
    @GetMapping("/myPlantsOverview")
    public String myPlantsOverview(Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        model.addAttribute("userPlants", userService.findActivePlantsBySeller(currentUser.getUserId()));
        return "myPlantsOverview";
    }

    /**
     * This method retrieves all plants that the current user has bought and displays them on the myPlants page.
     * @param model The model that is sent to the view.
     * @return "myPlants", the view with all plants that the current user has bought.
     */
    @GetMapping("/myPlants")
    public String myBoughtPlants(Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        List<Plant> boughtPlants = currentUser.getPurchasedPlants();
        model.addAttribute("boughtPlants", boughtPlants);
        return "myPlants";
    }
}