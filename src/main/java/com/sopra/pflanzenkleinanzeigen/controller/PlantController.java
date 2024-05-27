package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This controller is responsible for managing plants.
 * It provides endpoints for retrieving all plants, for retrieving a specific plant, for creating a new plant,
 * for updating a plant and for deleting a plant.
 */

@Controller
public class PlantController {

    @Autowired
    private PlantService plantService;

    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);


    //TODO: FRAGEN: wie sollen wir "Get all Plants" implementieren? mit @ModelAttribute
    // oder mit @GetMapping und dann model.Attribute?
    /**
     * This method retrieves all plants.
     * @return A list of all plants.
     */
    @ModelAttribute("plantsNOTUSED")
    public List<Plant> getAllPlants() {
        return plantService.findAllPlants();
    }

    /**
     * This method retrieves all plants and displays them on the plants page.
     * @param model The model that is sent to the view.
     * @return "plants", the view with all plants.
     */
    @GetMapping("/plants")
    public String getPlants(Model model) {
        try {
            model.addAttribute("allPlants", plantService.findAllPlants());
        } catch (Exception getPlantException ) {
            logger.error("Fehler beim Abrufen der Pflanzen", getPlantException);
            model.addAttribute("error", "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
            return "error";
        }
        return "plants";
    }

    /**
     * This method retrieves a specific plant by its ID and displays its details.
     * @param id The ID of the plant to be retrieved.
     * @param model The model that is sent to the view.
     * @return "detailsPlant", the view with the details of the specific plant.
     */
    @GetMapping("/plants/{id}")
    public String getPlantDetails(@PathVariable int id, Model model) {
        Plant plant = plantService.findPlantById(id);
        if (plant == null) {
            return "redirect:/plants";
        }
        model.addAttribute("plant", plant);
        return "detailsPlant";
    }

    //TODO: FRAGEN: warum braucht man diesen zwischenschritt
    /**
     * This method displays the form for creating a new plant.
     * @param model The model that is sent to the view.
     * @return "createPlant", the view with the form for creating a new plant.
     */
    @GetMapping("/plants/new")
    public String createPlantForm(Model model) {
        model.addAttribute("newPlant", new Plant());
        return "createPlant";
    }

    /**
     * This method adds a new plant.
     * @param newPlant The new plant to be added.
     * @param result The result of the validation of the new plant.
     * @param model The model that is sent to the view.
     * @return "redirect:/plants", the view with all plants, if the new plant is valid. Otherwise, it returns "createPlant", the view with the form for creating a new plant.
     */
    @PostMapping("/plants")
    public String addPlant(@Valid @ModelAttribute("newPlant") Plant newPlant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newPlant", newPlant);
            return "createPlant";
        }
        plantService.savePlant(newPlant);
        return "redirect:/plants";
    }

    /**
     * This method displays the form for editing an existing plant.
     * @param id The ID of the plant to be edited.
     * @param model The model that is sent to the view.
     * @return "editPlant", the view with the form for editing the plant, if the plant exists. Otherwise, it returns "redirect:/plants", the view with all plants.
     */
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
    /**
     * This method updates an existing plant.
     * @param id The ID of the plant to be updated.
     * @param plantToUpdate The updated plant.
     * @param result The result of the validation of the updated plant.
     * @param model The model that is sent to the view.
     * @return "redirect:/plants", the view with all plants, if the updated plant is valid. Otherwise, it returns "editPlant", the view with the form for editing the plant.
     */
    @PostMapping("/plants/edit/{id}")
    public String updatePlant(@PathVariable int id, @Valid @ModelAttribute("plantToUpdate") Plant plantToUpdate, BindingResult result, Model model) {
        model.addAttribute("plantToUpdate", plantToUpdate);
        plantToUpdate.setPlantId(id);
        if (result.hasErrors()) {
            return "editPlant";
        }
        plantService.savePlant(plantToUpdate);
        return "redirect:/plants";
    }

    /**
     * This method deletes a specific plant by its ID.
     * @param id The ID of the plant to be deleted.
     * @return "redirect:/plants", the view with all plants.
     */
    @GetMapping("/plants/delete/{id}")
    public String deletePlant(@PathVariable int id) {
        try {
            plantService.deletePlantById(id);
        } catch (Exception e) {
            logger.error("Fehler beim Löschen der Pflanze mit der ID: " + id, e);
            // TODO: Weiterleitung zu einer Fehlerseite oder Anzeige einer Fehlermeldung auf der aktuellen Seite:
            //  Der Fehler wird zwar im Log protokolliert, aber der Benutzer wird lediglich auf die Pflanzenübersichtsseite weitergeleitet, ohne eine spezifische Fehlermeldung zu erhalten.
            return "redirect:/plants";
        }
        return "redirect:/plants";
    }
}