package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.repository.CareTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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