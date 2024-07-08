package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface provides methods to interact with the User entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the User entity.
 */
public interface UserRepository extends JpaRepository<Benutzer, Integer> {

    Benutzer findByUsername(String username);

    @Query("SELECT p FROM Plant p WHERE p.seller.userId = :userId")
    List<Plant> findPlantsBySeller(@Param("userId") Integer userId);

    @Query("SELECT p FROM Plant p WHERE p.seller.userId = :userId AND p.sold = true")
    List<Plant> findPlantsBySellerAndSold(@Param("userId") Integer userId);

}