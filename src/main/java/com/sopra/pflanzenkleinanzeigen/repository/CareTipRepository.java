package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.CareTip;
import com.sopra.pflanzenkleinanzeigen.entity.Category;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface provides methods to interact with the CareTip entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the CareTip entity.
 */
public interface CareTipRepository extends JpaRepository<CareTip, Integer> {
    @Query("SELECT c FROM CareTip c WHERE LOWER(c.plantName) LIKE LOWER(CONCAT('%', :plantName, '%'))")
    List<CareTip> findByKeywordPlantName(@Param("plantName") String plantName);
}