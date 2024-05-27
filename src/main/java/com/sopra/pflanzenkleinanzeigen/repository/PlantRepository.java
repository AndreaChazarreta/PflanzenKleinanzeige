package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query("SELECT p FROM Plant p WHERE p.name LIKE %?1%")
    public List<Plant> findByKeyword(@Param("keyword") String keyword);

}

