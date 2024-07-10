package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface provides methods to interact with the Chat entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Chat entity.
 */
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c " +
            "WHERE c.plant.seller.userId = :userId OR c.possibleBuyer.userId = :userId " +
            "ORDER BY c.lastActivity DESC")
    List<Chat> findChatsByUserId(@Param("userId") int userId);


}
