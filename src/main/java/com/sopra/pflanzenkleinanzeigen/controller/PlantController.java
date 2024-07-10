package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.entity.Category;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.PdfService;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CareTipService careTipService;

    private Boolean created = false;
    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

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
                            @RequestParam(value = "excludeCurrentUser", required = false) Boolean excludeCurrentUser,
                            @RequestParam(value = "fruits", required = false) Boolean fruits,
                            @RequestParam(value = "airPurifying", required = false) Boolean airPurifying,
                            @RequestParam(value = "toxicForPets", required = false) Boolean toxicForPets,
                            @RequestParam(value = "sortPrice", required = false) String sortPrice) {
        try {
            Benutzer currentUser = userService.getCurrentUser();
            model.addAttribute("currentUser", currentUser);
            List<Plant> plants = new ArrayList<>();

            if(categories != null && !categories.isEmpty()){
                plants = plantService.findPlantsByFilters(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, categories, excludeCurrentUser, currentUser,  fruits, airPurifying, toxicForPets, sortPrice);
            } else{
                plants = plantService.findPlantsByFiltersWithoutCategory(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, excludeCurrentUser, currentUser,  fruits, airPurifying, toxicForPets, sortPrice);
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
            model.addAttribute("excludeCurrentUser", excludeCurrentUser);
            model.addAttribute("fruits", fruits);
            model.addAttribute("airPurifying", airPurifying);
            model.addAttribute("toxicForPets", toxicForPets);

            if(created){
                model.addAttribute("created", created);
                created = false;
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
        Benutzer currentUser = userService.getCurrentUser();
        if (plant == null) {
            return "redirect:/plants";
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("plant", plant);
        return "detailsPlant";
    }

    /**
     * This method displays the form for creating a new plant.
     * @param model The model that is sent to the view.
     * @return "createPlant", the view with the form for creating a new plant.
     */
    @GetMapping("/plants/new")
    public String createPlantForm(Model model) {
        model.addAttribute("newPlant", new Plant());
        List<CareTip> allCaretips = careTipService.findAllCareTips();
        model.addAttribute("allCaretips", allCaretips);
        List<Category> allCategories = categoryService.findAllCategories();
        model.addAttribute("allCategories", allCategories);
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
                           @RequestParam(value = "category", required = false) Integer categoryId, @RequestParam(value = "lifespan", required = false) String lifespan,
                           @RequestParam(value = "floweringTime", required = false) String floweringTime, @RequestParam(value = "growthRate", required = false) String growthRate,
                           @RequestParam(value = "usability", required = false) String usability, @RequestParam(value = "color", required = false) String color,
                           @RequestParam(value = "leafShape", required = false) String leafShape, @RequestParam(value = "standort", required = false) String standort,
                           @RequestParam(value = "selectedCaretip", required = false) String caretipName){
        if (result.hasErrors()) {
            model.addAttribute("newPlant", newPlant);
            return "createPlant";
        }
        try {
            newPlant.setSeller(userService.getCurrentUser());
            CareTip careTip = careTipService.findCareTipByKeyName(caretipName);
            newPlant.setAdIsActive(true);
            newPlant.setCreatedAt(Instant.now());
            newPlant.setCategory(categoryService.findById(categoryId));
            newPlant.setLifespan(lifespan);
            newPlant.setFloweringTime(floweringTime);
            newPlant.setGrowthRate(growthRate);
            newPlant.setCareTip(careTip);
            newPlant.setUsability(usability);
            newPlant.setColor(color);
            newPlant.setLeafShape(leafShape);
            newPlant.setStandort(standort);
            plantService.savePlant(newPlant, imageFile);
        } catch (IOException e) {
            model.addAttribute("error", "Ein Fehler ist aufgetreten: " + e.getMessage());
            return "createPlant";
        }
        created = true;
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
        plantToUpdate.setCareTip(oldPlant.getCareTip());
        plantToUpdate.setCreatedAt(Instant.now());
        if(imageFile == null || imageFile.isEmpty()) {
            plantToUpdate.setImagePath(oldPlant.getImagePath());
        }
        plantToUpdate.setAdIsActive(oldPlant.isAdIsActive());
        if (result.hasErrors()) {
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

    /**
     * Diese Methode markiert eine Pflanze als gewünscht für den aktuellen Benutzer.
     * Sie wird aufgerufen, wenn eine POST-Anforderung an "/plants/wishlist/{id}" gesendet wird.
     *
     * @param id Die ID der Pflanze, die als gewünscht markiert werden soll.
     *
     * Der aktuelle Benutzer wird durch den Aufruf von userService.getCurrentUser() ermittelt.
     * Die Pflanze wird durch den Aufruf von plantService.findPlantById(id) ermittelt.
     *
     * Wenn die Pflanze existiert und der aktuelle Benutzer sie noch nicht als gewünscht markiert hat,
     * wird die Pflanze durch den Aufruf von plantService.markPlantAsWished(currentUser, plant) als gewünscht markiert.
     */
    @PostMapping("/plants/wishlist/{id}")
    @ResponseBody
    public void markPlantAsWished(@PathVariable int id) {
        Benutzer currentUser = userService.getCurrentUser();
        Plant plant = plantService.findPlantById(id);
        if (plant != null && !plant.getWishedBy().contains(currentUser)) {
            plantService.markPlantAsWished(currentUser, plant);
        }
    }

    /**
     * This method unmarks a plant as wished for the current user.
     * It is invoked when a POST request is sent to "/plants/wishlist/remove/{id}".
     *
     * @param id The ID of the plant to be unmarked as wished.
     *
     * The current user is determined by calling userService.getCurrentUser().
     * The plant is determined by calling plantService.findPlantById(id).
     *
     * If the plant exists and the current user has marked it as wished,
     * the plant is unmarked as wished by calling plantService.unmarkPlantAsWished(currentUser, plant).
     */
    @PostMapping("/plants/wishlist/remove/{id}")
    @ResponseBody
    public void unmarkPlantAsWished(@PathVariable int id) {
        Benutzer currentUser = userService.getCurrentUser();
        Plant plant = plantService.findPlantById(id);
        if (plant != null && plant.getWishedBy().contains(currentUser)) {
            plantService.unmarkPlantAsWished(currentUser, plant);
        }
    }

    /**
     * This method retrieves the wishlist of the current user.
     * It is invoked when a GET request is sent to "/myWishlist".
     *
     * The current user is determined by calling userService.getCurrentUser().
     * The wishlist is retrieved by calling plantService.getWishlistForUser(currentUser).
     *
     * The wishlist is then sorted based on whether the plant ad is active and the date the plant was wished for.
     * The sorted wishlist is added to the model and sent to the view.
     *
     * @param model The Model object used to pass attributes to the view.
     * @return "myWishlist", the view displaying the user's wishlist.
     */
    @GetMapping("/myWishlist")
    public String getWishlistForUser(Model model) {
        Benutzer currentUser = userService.getCurrentUser();
        List<Plant> wishlist = plantService.getWishlistForUser(currentUser);

        List<Plant> sortedWishlist = wishlist.stream()
                .sorted((p1, p2) -> {
                    if (p1.isAdIsActive() != p2.isAdIsActive()) {
                        return Boolean.compare(p2.isAdIsActive(), p1.isAdIsActive());
                    } else {
                        return p2.getDateWished().compareTo(p1.getDateWished()); // Sort descending by dateWished
                    }
                }).collect(Collectors.toList());

        model.addAttribute("wishlist", sortedWishlist);
        return "myWishlist";
    }

    /**
     * This method searches for plants in the wishlist of the current user by name.
     * It is invoked when a GET request is sent to "/searchWishlist".
     *
     * @param model The Model object used to pass attributes to the view.
     * @param name The name of the plant to search for in the wishlist.
     * @return "myWishlist", the view displaying the user's wishlist.
     *
     * The current user is determined by calling userService.getCurrentUser().
     * The wishlist is retrieved by calling plantService.searchWishlistPlantsByName(currentUser, name).
     *
     * The wishlist and the name of the plant are then added to the model and sent to the view.
     */
    @GetMapping("/searchWishlist")
    public String searchWishlistPlants(Model model, @RequestParam(value = "name") String name) {
        Benutzer currentUser = userService.getCurrentUser();
        List<Plant> wishlist = plantService.searchWishlistPlantsByName(currentUser, name);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("name", name);
        return "myWishlist";
    }

    /**
     * This method generates a PDF for a specific plant and returns it as a response entity.
     * It is invoked when a GET request is sent to "/plants/pdf/{id}".
     *
     * @param id The ID of the plant for which the PDF is to be generated.
     * @return ResponseEntity<InputStreamResource> This returns a response entity containing the PDF as an input stream resource.
     * If the plant does not exist, it returns a not found response entity.
     *
     * The plant is determined by calling plantService.findPlantById(id).
     * If the plant exists, a PDF is generated by calling pdfService.generatePlantPdf(plant).
     *
     * The PDF is then added to the response entity with the appropriate headers and content type.
     */
    @GetMapping("/plants/pdf/{id}")
    public ResponseEntity<InputStreamResource> getPlantPdf(@PathVariable int id) {
        Plant plant = plantService.findPlantById(id);
        if (plant == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayInputStream bis = pdfService.generatePlantPdf(plant);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=plant-pflegetipps.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}