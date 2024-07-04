package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import com.sopra.pflanzenkleinanzeigen.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ChatService chatService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CareTipService careTipService;


    //TODO: brauchen wir für solche funktionen try and catch? Hier kann relativ wenig schief gehen
    /**
     * This method retrieves all plants and displays them on the plants page if they are still active.
     * @param model The model that is sent to the view.
     * @return "plants", the view with all plants.
     */
    @GetMapping("/plants")
    public String getPlants(Model model,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                            @RequestParam(value = "minHeight", required = false) BigDecimal minHeight,
                            @RequestParam(value = "maxHeight", required = false) BigDecimal maxHeight,
                            @RequestParam(value = "potIncluded", required = false) Boolean potIncluded,
                            @RequestParam(value = "category", required = false) List<String> categories,
                            @RequestParam(value = "sortPrice", required = false) String sortPrice) {
        try {
            Benutzer currentUser = userService.getCurrentUser();
            model.addAttribute("currentUser", currentUser);
            List<Plant> plants = new ArrayList<>();

            if(categories != null && !categories.isEmpty()){
                plants = plantService.findPlantsByFilters(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, categories, sortPrice);
            } else{
                plants = plantService.findPlantsByFiltersWithoutCategory(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, sortPrice);
            }

            BigDecimal highestPrice = plantService.findMaxPrice();
            if (highestPrice.compareTo(new BigDecimal(200)) > 0) {
                highestPrice = new BigDecimal(200);
            }

            model.addAttribute("plants", plants);
            model.addAttribute("name", name);
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
            model.addAttribute("minHeight", minHeight);
            model.addAttribute("maxHeight", maxHeight);
            model.addAttribute("potIncluded", potIncluded);
            model.addAttribute("categories", categories);
            model.addAttribute("sortPrice", sortPrice);
            model.addAttribute("highestPrice", highestPrice);

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
        Benutzer currentUser = userService.getCurrentUser();
        if (plant == null) {
            return "redirect:/plants";
        }
        model.addAttribute("currentUser", currentUser);
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
    public String addPlant(@Valid @ModelAttribute("newPlant") Plant newPlant, @RequestParam("imageFile") MultipartFile imageFile, BindingResult result, Model model,
                           @RequestParam(value = "category", required = false) Integer categoryId) {
        if (result.hasErrors()) {
            //TODO: schauen warum es hier Beschreibung leer lassen als Fehler angenommen wird
            model.addAttribute("newPlant", newPlant);
            return "createPlant";
        }
        try {
            newPlant.setSeller(userService.getCurrentUser());
            CareTip careTip = careTipService.findCareTipByKeyName(newPlant.getName());
            newPlant.setAdIsActive(true);
            newPlant.setCategory(categoryService.findById(categoryId));
            newPlant.setCareTip(careTip);
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
        Benutzer currentUser = userService.getCurrentUser();
        if (!plantToUpdate.getSeller().equals(currentUser)) {
            logger.error("Benutzer ist nicht berechtigt, die Pflanze zu bearbeiten.");
            model.addAttribute("error", "Sie sind nicht berechtigt, diese Pflanze zu bearbeiten.");
            return "error";
        }
        model.addAttribute("plantToUpdate", plantToUpdate);
        return "editPlant";
    }

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
        Plant plant = plantService.findPlantById(id);
        if(!(plant.getChatsAboutThisPlant().isEmpty() && plant.getWishedBy().isEmpty() && plant.getBuyer() == null)){
            plant.setAdIsActive(false);
            plantService.savePlantDataLoader(plant);
        }else{
            plantService.deletePlant(plant);
        }
        return "redirect:/myPlantsForSale";
    }


    @PostMapping("/plants/wishlist/{id}")
    @ResponseBody
    public void markPlantAsWished(@PathVariable int id) {
        Benutzer currentUser = userService.getCurrentUser();
        Plant plant = plantService.findPlantById(id);
        if (plant != null && !plant.getWishedBy().contains(currentUser)) {
            plantService.markPlantAsWished(currentUser, plant);
        }
    }

    @PostMapping("/plants/wishlist/remove/{id}")
    @ResponseBody
    public void unmarkPlantAsWished(@PathVariable int id) {
        Benutzer currentUser = userService.getCurrentUser();
        Plant plant = plantService.findPlantById(id);
        if (plant != null && plant.getWishedBy().contains(currentUser)) {
            plantService.unmarkPlantAsWished(currentUser, plant);
        }
    }

    @GetMapping("/myWishlist")
    public String getWishlistForUser(Model model, @RequestParam(value = "name", required = false) String name) {
        Benutzer currentUser = userService.getCurrentUser();
        List<Plant> wishlist;

        if (name != null && !name.isEmpty()) {
            wishlist = plantService.getWishlistForUser(currentUser);
            model.addAttribute("name", name);
        } else {
            wishlist = plantService.getWishlistForUser(currentUser);
        }
        List<Plant> sortedWishlist = wishlist.stream()
                .sorted((p1, p2) -> Boolean.compare(p2.isAdIsActive(), p1.isAdIsActive()))
                .collect(Collectors.toList());

        model.addAttribute("wishlist", sortedWishlist);
        return "myWishlist";
    }

}