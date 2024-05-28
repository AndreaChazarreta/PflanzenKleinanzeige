package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods to interact with the User entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the User entity.
 */
public interface UserRepository extends JpaRepository<Benutzer, Integer> {

    Benutzer findByUsername(String username);
}