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
    @GetMapping("/myPlantsForSale")
    public String myPlantsForSaleOverview(@RequestParam(value = "name", required = false) String name, Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        List<Plant> plants;
        if (name != null && !name.isEmpty()) {
            plants = userService.findPlantsByNameAndSeller(name, currentUser.getUserId());
            model.addAttribute("name", name);
        } else {
            plants = userService.findActivePlantsBySeller(currentUser.getUserId());
        }
        model.addAttribute("userPlants", plants);
        return "myPlantsForSale";
    }

    /**
     * This method retrieves all plants that the current user has bought and displays them on the myPlants page.
     * @param model The model that is sent to the view.
     * @return "myPlants", the view with all plants that the current user has bought.
     */
    @GetMapping("/myPlants")
    public String myPlantsOverview(@RequestParam(value = "name", required = false) String name, Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        List<Plant> boughtPlants;
        if (name != null && !name.isEmpty()) {
            boughtPlants = userService.findPurchasedPlantsByNameAndBuyer(name, currentUser.getUserId());
            model.addAttribute("name", name);
        } else {
            boughtPlants = currentUser.getPurchasedPlants();
        }
        model.addAttribute("boughtPlants", boughtPlants);
        return "myPlants";
    }

    @GetMapping("/mySoldPlants")
    public String mySoldPlantsOverview(Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/";
        }
        List<Plant> soldPlants = userService.findSoldPlantsBySeller(currentUser.getUserId());
        model.addAttribute("soldPlants", soldPlants);
        return "mySoldPlants";
    }
}