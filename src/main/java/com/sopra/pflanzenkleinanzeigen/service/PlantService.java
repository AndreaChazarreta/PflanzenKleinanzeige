package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/plant-images/";

    public Plant savePlant(@Valid Plant plant, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            plant.setImagePath(imagePath);
        } else {
            if (plant.getImagePath() != null) {
                plant.setImagePath(plant.getImagePath());
            } else {
                plant.setImagePath("/plant-images/defaultImage.jpg");
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

    public Plant findPlantById(Integer id) {
        return plantRepository.findById(id).orElse(null);
    }

    public void deletePlant(Plant plant) {
        plantRepository.delete(plant);
    }

    public List<Plant> findByKeywordName(String name) {
        if (name != null) {
            return plantRepository.findByKeywordName(name.toLowerCase());
        } else {
            return plantRepository.findAll();
        }
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String randomFilename = UUID.randomUUID().toString();
        String imagePath = IMAGE_DIRECTORY + randomFilename;
        Path path = Paths.get(imagePath);
        Files.write(path, imageFile.getBytes());
        return "/plant-images/" + randomFilename;
    }
}
