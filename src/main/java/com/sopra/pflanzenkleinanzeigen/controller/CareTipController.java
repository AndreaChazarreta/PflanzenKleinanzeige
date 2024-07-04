package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.entity.Category;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.CareTipService;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The PlantController class handles web requests related to plant operations.
 * It interacts with the PlantService to manage plant information, including
 * retrieving, creating, updating, and deleting plants, and preparing the data for display in the view Layer.
 */
@Controller
public class CareTipController {

    @Autowired
    private CareTipService careTipService;

    private static final Logger logger = LoggerFactory.getLogger(CareTipController.class);

    @Autowired
    private PlantService plantService;


    /**
     * This method retrieves a specific plant by its ID and displays the care tip.
     *
     * @param id    The ID of the plant to be retrieved.
     * @param model The model that is sent to the view.
     * @return "careTip", the view with the careTip of the specific plant.
     */
    @GetMapping("/plants/careTip/{id}")
    public String getCareTip(@PathVariable int id, Model model) {
        Plant plant = plantService.findPlantById(id);
        CareTip careTip = plant.getCareTip();

        if (careTip == null) {
            return "redirect:/myPlants";
        }

        model.addAttribute("plant", plant);
        model.addAttribute("careTip", careTip);
        return "careTip";
    }
}