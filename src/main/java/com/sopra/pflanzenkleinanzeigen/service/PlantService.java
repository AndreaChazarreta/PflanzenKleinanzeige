package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    public Plant savePlant(Plant plant) {
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