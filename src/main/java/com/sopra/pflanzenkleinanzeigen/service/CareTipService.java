package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.repository.CareTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents a care tip service in the system.
 * It contains methods to save a care tip and to find all care tips.
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