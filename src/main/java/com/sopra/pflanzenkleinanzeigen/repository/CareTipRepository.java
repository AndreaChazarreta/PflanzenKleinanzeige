package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods to interact with the CareTip entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the CareTip entity.
 */
public interface CareTipRepository extends JpaRepository<CareTip, Integer> {

}