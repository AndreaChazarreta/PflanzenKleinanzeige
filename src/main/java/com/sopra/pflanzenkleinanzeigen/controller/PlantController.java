package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The PlantController class handles web requests related to plant operations.
 * It interacts with the PlantService to manage plant information, including
 * retrieving, creating, updating, and deleting plants, and preparing the data for display in the view Layer.
 */
@Controller
public class PlantController {

    @Autowired
    private PlantService plantService;

    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);
    @Autowired
    private UserService userService;


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

    //TODO: brauchen wir für solche funktionen try and catch? Hier kann relativ wenig schief gehen
    /**
     * This method retrieves all plants and displays them on the plants page.
     * @param model The model that is sent to the view.
     * @return "plants", the view with all plants.
     */
    @GetMapping("/plants")
    public String getPlants(Model model, @RequestParam(value = "name", required = false) String name) {
        try {
            if(name != null && !name.isEmpty()){
                model.addAttribute("plantsByName", plantService.findByKeywordName(name));
                model.addAttribute("name", name);
            } else {
                model.addAttribute("allPlants", plantService.findAllPlants());
            }
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
    public String addPlant(@Valid @ModelAttribute("newPlant") Plant newPlant, @RequestParam("imageFile") MultipartFile imageFile, BindingResult result, Model model) {
        if (result.hasErrors()) {
            //TODO: schauen warum es hier Beschreibung leer lassen als Fehler angenommen wird
            model.addAttribute("newPlant", newPlant);
            return "createPlant";
        }
        try {
            newPlant.setSeller(userService.getCurrentUser());
            newPlant.setAdIsActive(true);
            plantService.savePlant(newPlant, imageFile);
        } catch (IOException e) {
            model.addAttribute("error", "Ein Fehler ist aufgetreten: " + e.getMessage());
            return "createPlant";
        }
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
    public String updatePlant(@PathVariable int id, @Valid @ModelAttribute("plantToUpdate") Plant plantToUpdate, @RequestParam("imageFile") MultipartFile imageFile, BindingResult result, Model model) {
        model.addAttribute("plantToUpdate", plantToUpdate);
        plantToUpdate.setPlantId(id);
        Plant oldPlant = plantService.findPlantById(id);
        plantToUpdate.setSeller(oldPlant.getSeller());
        if(imageFile == null || imageFile.isEmpty()) {
            plantToUpdate.setImagePath(oldPlant.getImagePath());
        }
        //TODO: das kann man löschen sobald man es richtig in frontend implementiert hat
        plantToUpdate.setAdIsActive(oldPlant.isAdIsActive());
        if (result.hasErrors()) {
            //TODO: schauen warum es hier Beschreibung leer lassen als Fehler angenommen wird
            return "editPlant";
        }
        try{
            plantService.savePlant(plantToUpdate, imageFile);
        } catch (IOException e) {
            model.addAttribute("error", "Ein Fehler ist aufgetreten: " + e.getMessage());
            return "editPlant";
        }
        return "redirect:/plants";
    }

    /**
     * This method deletes a plant by its ID.
     * @param id The ID of the plant to be deleted.
     * @return "redirect:/plants", the view with all plants.
     */
    //TODO: Post oder get?
    @PostMapping("/plants/delete/{id}")
    public String deletePlant(@PathVariable int id, Model model) {
        try {
            Plant plant = plantService.findPlantById(id);
            if(plant == null) {
                logger.error("Pflanze mit ID: " + id + " wurde nicht gefunden.");
                model.addAttribute("error", "Fehler beim Abrufen der Pflanze mit Id: " + id);
                return "redirect:/plants";
            }
            Benutzer currentUser = userService.getCurrentUser();
            if (!plant.getSeller().equals(currentUser)) {
                logger.error("Benutzer ist nicht berechtigt, die Pflanze zu löschen.");
                model.addAttribute("error", "Sie sind nicht berechtigt, diese Pflanze zu löschen.");
                return "error";
            }
            //TODO: unterschiedliche Fehlermeldungen pro case oder möchten wir das lieber in frontend handeln?
            if(!(plant.getChatsAboutThisPlant().isEmpty() && plant.getWishedBy().isEmpty() && plant.getBuyer() == null)){
                logger.error("Man darf diese Pflanze nicht löschen, da es chats und/oder Merkzetteln zu diesen Pflanze existieren oder diese Pflanze wurde verkauft.");
                model.addAttribute("error", "Sie dürfen diese Pflanze nicht löschen, aber sie können die archivieren.");
                return "error";
            }
            plantService.deletePlant(plant);
        } catch (Exception deletePlantException) {
            logger.error("Fehler beim Löschen der Pflanze", deletePlantException);
            model.addAttribute("error", "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
            return "error";
        }
        return "redirect:/plants";
    }
}