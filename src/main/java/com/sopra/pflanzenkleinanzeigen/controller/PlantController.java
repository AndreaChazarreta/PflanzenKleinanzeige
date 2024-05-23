package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/plants/{id}")
    public String getPlantDetails(@PathVariable int id, Model model) {
        Plant plant = plantService.findPlantById(id);
        if (plant == null) {
            // Handle case where plant is not found, maybe redirect to an error page or back to the plants list
            return "redirect:/plants";
        }
        model.addAttribute("plant", plant);
        return "detailsPlant";
    }

    //TODO: FRAGEN: warum braucht man diesen zwischenschritt
    @GetMapping("/plants/new")
    public String createPlantForm(Model model) {
        model.addAttribute("newPlant", new Plant());
        return "createPlant";
    }

    @PostMapping("/plants")
    public String addPlant(@Valid @ModelAttribute("newPlant") Plant newPlant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newPlant", newPlant);
            return "createPlant";
        }
        plantService.savePlant(newPlant);
        return "redirect:/plants";
    }

    @GetMapping("/plants/edit/{id}")
    public String editPlantForm(@PathVariable int id, Model model) {
        Plant plantToUpdate = plantService.findPlantById(id);
        if (plantToUpdate == null) {
            return "redirect:/plants";
        }
        model.addAttribute("plantToUpdate", plantToUpdate);
        return "editPlant";
    }

    //TODO: schauen ob wir hier @PathVariable (ich glaube das ist bestPractice) benutzen oder @RequestParam (sowie in die Vorlesung)
    @PostMapping("/plants/edit/{id}")
    public String updatePlant(@PathVariable int id, @Valid @ModelAttribute("plantToUpdate") Plant plantToUpdate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("plantToUpdate", plantToUpdate);
            return "editPlant";
        }
        plantToUpdate.setPlantId(id);
        plantService.savePlant(plantToUpdate);
        return "redirect:/plants";
    }

    @GetMapping("/plants/delete/{id}")
    public String deletePlant(@PathVariable int id) {
        plantService.deletePlantById(id);
        return "redirect:/plants";
    }
}