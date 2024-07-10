package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This interface provides methods to interact with the Message entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat.chatId = :chatId AND m.sender.userId != :userId AND m.read = false")
    int countUnreadMessages(@Param("chatId") int chatId, @Param("userId") int userId);
}
