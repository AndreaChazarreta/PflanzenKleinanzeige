package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods to interact with the Role entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Role entity.
 */
public interface RoleRepository extends JpaRepository<Rolle, Integer> {

}