package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class encapsulates access to the PlantRepository. It provides methods for managing Plant entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;


    public Plant savePlant(@Valid Plant plant) {
        return plantRepository.save(plant);
    }

    public List<Plant> findAllPlants() {
        return plantRepository.findAll();
    }

    public Plant findPlantById(Integer id) {
        return plantRepository.findById(id).orElse(null);
    }

    @PreAuthorize("#plant.seller.username == authentication.name")
    public void deletePlant(Plant plant) {
        plantRepository.delete(plant);
    }

    public List<Plant> findByKeyword(String keyword) {
        if (keyword != null) {
            return plantRepository.findByKeyword(keyword);
        } else {
            return plantRepository.findAll();
        }
    }
}
