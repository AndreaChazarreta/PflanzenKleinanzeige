package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PlantController {

    @Autowired
    private PlantService plantService;

    //TODO: FRAGEN: wie sollen wir "Get all Plants" implementieren? mit @ModelAttribute
    // oder mit @GetMapping und dann model.Attribute?
    @ModelAttribute("plantsNOTUSED")
    public List<Plant> getAllPlants() {
        return plantService.findAllPlants();
    }

    /**
     * Zeigt die Pflanzen Seite mit allen Pflanzen an.
     * @return die View mit allen Pflanzen (also die plants.html).
     */
    @GetMapping("/plants")
    public String getPlants(Model model) {
        model.addAttribute("allPlants", plantService.findAllPlants());
        return "plants";
    }

    @GetMapping("/plants/new")
    public String createPlantForm(Model model) {
        model.addAttribute("newPlant", new Plant());
        return "createPlant";
    }

    @PostMapping("/plants")
    public String addPlant(@Valid @ModelAttribute("newPlant") Plant newPlant) {
        plantService.savePlant(newPlant);
        return "redirect:/plants";
    }
}