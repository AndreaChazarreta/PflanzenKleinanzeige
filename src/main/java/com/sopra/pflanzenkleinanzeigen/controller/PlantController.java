package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PlantController {
    @Autowired
    private PlantService plantService;
    @Autowired
    private PlantRepository plantRepository;

    @ModelAttribute("plants")
    public List<Plant> getAllPlants() {
        return plantService.findAllPlants();
    }

    /**
     * Zeigt die Pflannzenseite mit allen Pflanzen an.
     * @return all plants.
     */
    @GetMapping("/plants")
    public List<Plant> getPlants() {

        List<Plant> allPlant = plantRepository.findAll();
        return allPlant;
    }
    @PostMapping("/plant")
    @ResponseStatus(HttpStatus.CREATED)
    public Plant createAssignee(@Valid @RequestBody Plant requestBody) {
        Plant plant = new Plant(requestBody.getName(), requestBody.getPrice(), requestBody.getHeight(),
                requestBody.getDescription());
        Plant savedPlant = plantService.savePlant(plant);
        return savedPlant;
    }
}