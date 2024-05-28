package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.repository.CareTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class encapsulates access to the CareTipRepository. It provides methods for managing CareTip entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class CareTipService {

    @Autowired
    private CareTipRepository careTipRepository;

    public CareTip saveCareTip(CareTip careTip) {
        return careTipRepository.save(careTip);
    }

    public List<CareTip> findAllCareTips() {
        return careTipRepository.findAll();
    }
}