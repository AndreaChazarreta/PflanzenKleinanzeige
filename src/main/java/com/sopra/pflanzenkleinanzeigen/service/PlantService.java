package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import com.sopra.pflanzenkleinanzeigen.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * This class encapsulates access to the PlantRepository. It provides methods for managing Plant entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/plant-images/";

    /**
     * Saves a plant to the database. If an image was uploaded, this image is saved and the path to the image is set in the plant.
     * If no image was uploaded but the plant already has an image, the image path remains unchanged.
     * If no image was uploaded and the plant has no image, a default image path is set.
     *
     * @param plant The plant object to be saved. It must be valid (i.e., it has all required fields filled out).
     * @param imageFile The uploaded image. It can be null.
     * @return The saved plant. It includes all generated fields, such as the ID.
     * @throws IOException If an error occurs while saving the image.
     */
    public Plant savePlant(@Valid Plant plant, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            plant.setImagePath(imagePath);
        } else {
            if (plant.getImagePath() != null) {
                plant.setImagePath(plant.getImagePath());
            } else {
                plant.setImagePath("/plant-images/noImage.jpg");
            }
        }
        return plantRepository.save(plant);
    }

    public Plant savePlantDataLoader(@Valid Plant plant){
        return plantRepository.save(plant);
    }

    public List<Plant> findAllPlants() {
        return plantRepository.findAll();
    }

    public List<Plant> findAllActivePlants() {
        return plantRepository.findAllActivePlants();
    }

    public Plant findPlantById(Integer id) {
        return plantRepository.findById(id).orElse(null);
    }

    public List<Plant> findPlantsByFilters(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                           BigDecimal minHeight, BigDecimal maxHeight,
                                           Boolean potIncluded, List<String> categories,
                                           Boolean excludeCurrentUser, Benutzer currentUser,
                                           Boolean fruits, Boolean airPurifying, Boolean toxicForPets, String sortPrice) {
        return plantRepository.findByFilters(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, categories, excludeCurrentUser, currentUser, fruits,  airPurifying,  toxicForPets, sortPrice);
    }

    public List<Plant> findPlantsByFiltersWithoutCategory(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                           BigDecimal minHeight, BigDecimal maxHeight, Boolean potIncluded,
                                           Boolean excludeCurrentUser, Benutzer currentUser,
                                           Boolean fruits, Boolean airPurifying, Boolean toxicForPets, String sortPrice) {
        return plantRepository.findByFiltersWithoutCategory(name, minPrice, maxPrice, minHeight, maxHeight, potIncluded, excludeCurrentUser, currentUser, fruits,  airPurifying,  toxicForPets, sortPrice);
    }

    public BigDecimal findMaxPrice() {
        return plantRepository.findMaxPrice();
    }

    public void deletePlant(Plant plant) {
        plantRepository.delete(plant);
    }

    /**
     * Searches for plants by a keyword in their name. If the keyword is null, it returns all plants.
     *
     * @param name The keyword used to search for plants. It can be null.
     * @return A list of plants that have the keyword in their name, or all plants if the keyword is null.
     */
    public List<Plant> findByKeywordName(String name) {
        if (name != null) {
            return plantRepository.findByKeywordName(name.toLowerCase());
        } else {
            return plantRepository.findAll();
        }
    }

    /**
     * This method searches for plants by their name.
     * If the name is not null and not empty, it returns plants whose names start with or contain the given name.
     * If the name is null or empty, it returns all plants.
     *
     * @param name The name to search for in the plants. It can be null or empty.
     * @return A list of plants that match the given name, or all plants if the name is null or empty.
     */
    public List<Plant> searchPlantsByName(String name) {
        List<Plant> result = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            result.addAll(plantRepository.findByPrefix(name));
            result.addAll(plantRepository.findByNameContaining(name, name));
        } else {
            result.addAll(plantRepository.findAll());
        }
        return result;
    }

    /**
     * This method searches for plants in a user's wishlist by name.
     * If the name is not null and not empty, it returns plants whose names start with or contain the given name.
     * If the name is null or empty, it returns all plants in the user's wishlist.
     *
     * @param user The user whose wishlist is to be searched.
     * @param name The name to search for in the wishlist.
     * @return A list of plants in the user's wishlist that match the given name, or all plants in the wishlist if the name is null or empty.
     */
    public List<Plant> searchWishlistPlantsByName(Benutzer user, String name) {
        List<Plant> wishlist = getWishlistForUser(user);

        // Filter results to ensure they are in the user's wishlist
        List<Plant> prefixResults = plantRepository.findByPrefix(name).stream()
                .filter(wishlist::contains)
                .collect(Collectors.toList());

        List<Plant> containsResults = plantRepository.findByNameContaining(name, name).stream()
                .filter(wishlist::contains)
                .collect(Collectors.toList());

        // Sort the final results: first by prefix, then by name
        List<Plant> finalResults = new ArrayList<>(prefixResults);
        finalResults.addAll(containsResults);

        return finalResults.stream()
                .sorted((p1, p2) -> {
                    boolean p1StartsWithPrefix = p1.getName().toLowerCase().startsWith(name.toLowerCase());
                    boolean p2StartsWithPrefix = p2.getName().toLowerCase().startsWith(name.toLowerCase());

                    if (p1StartsWithPrefix && !p2StartsWithPrefix) {
                        return -1;
                    } else if (!p1StartsWithPrefix && p2StartsWithPrefix) {
                        return 1;
                    } else {
                        return p1.getName().compareToIgnoreCase(p2.getName());
                    }
                }).collect(Collectors.toList());
    }


    /**
     * Saves an image file to a predefined directory and returns the path to the saved image.
     * The image file is saved with a random filename generated using UUID.
     *
     * @param imageFile The image file to be saved. It must not be null.
     * @return The path to the saved image file.
     * @throws IOException If an error occurs while writing the image file.
     */
    private String saveImage(MultipartFile imageFile) throws IOException {
        String randomFilename = UUID.randomUUID().toString();
        String imagePath = IMAGE_DIRECTORY + randomFilename;
        Path path = Paths.get(imagePath);
        Files.write(path, imageFile.getBytes());
        return "/plant-images/" + randomFilename;
    }
    /**
     * Marks a plant as wished for a specific user.
     * If the plant is not already in the user's wishlist, it is added.
     * Afterwards, the updated user and plant objects are saved in the database.
     *
     * @param user The user who is marking the plant as wished.
     * @param plant The plant that is being marked as wished.
     */
    public void markPlantAsWished(Benutzer user, Plant plant) {
        if (!plant.getWishedBy().contains(user)) {
            plant.getWishedBy().add(user);
            user.getWishedPlants().add(plant);
            plant.setDateWished(new Timestamp(System.currentTimeMillis()));
            plantRepository.save(plant);
            userRepository.save(user);
        }
    }

    /**
     * Removes the marking of a plant as wished for a specific user.
     * The plant is removed from the user's wishlist.
     * Afterwards, the updated user and plant objects are saved in the database.
     *
     * @param user The user for whom the marking of the plant as wished is being removed.
     * @param plant The plant whose marking as wished is being removed.
     */
    public void unmarkPlantAsWished(Benutzer user, Plant plant) {
        plant.getWishedBy().remove(user);
        user.getWishedPlants().remove(plant);
        plant.setDateWished(null);
        plantRepository.save(plant);
        userRepository.save(user);
    }

    public List<Plant> getWishlistForUser(Benutzer user) {
        return new ArrayList<>(user.getWishedPlants());
        }
}
