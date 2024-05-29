package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.ProfileIcon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods to interact with the ProfileIcon entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the ProfileIcon entity.
 */
public interface ProfileIconRepository extends JpaRepository<ProfileIcon, Integer> {

}
