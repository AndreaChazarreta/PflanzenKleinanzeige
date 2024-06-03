package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface provides methods to interact with the Plant entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Plant entity.
 */
public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query("SELECT p FROM Plant p WHERE p.name LIKE %?1%")
    public List<Plant> findByKeywordName(@Param("name") String name);

}

