package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class represents a plant service in the system.
 * It contains methods to save a plant, to find all plants, to find a plant by id and to delete a plant by id.
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

    public void deletePlantById(int id) {
        plantRepository.deleteById(id);
    }

    public List<Plant> findByKeyword(String keyword) {
        if (keyword != null) {
            return plantRepository.findByKeyword(keyword);
        } else {
            return plantRepository.findAll();
        }
    }
}
