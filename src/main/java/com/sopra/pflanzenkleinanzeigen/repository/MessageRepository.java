package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods to interact with the Message entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
