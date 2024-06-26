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
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
}
