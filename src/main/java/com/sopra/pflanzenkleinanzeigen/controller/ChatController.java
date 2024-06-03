package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The ChatController class handles web requests related to chat operations.
 * It interacts with the ChatService to manage chat information, including
 * retrieving, creating, and deleting chats, and preparing the data for display in the view Layer.
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;
    @Autowired
    private PlantService plantService;

    /**
     * This method shows the chats for one user
     * @param model The model that is sent to the view.
     * @return "chats", the view with all chats from one specific user.
     */
    @GetMapping("/chats")
    public String getChats (Model model){
        Benutzer currentBenutzer = userService.getCurrentUser();
        model.addAttribute("allChats", chatService.findUserChats(currentBenutzer.getUserId()));
        return "chats";
    }

    /**
     * This method shows the messages in a specific chat.
     * @param chatId The ID of the chat whose messages should be displayed.
     * @param model The model that is sent to the view.
     * @return "messages", the view with all messages in a specific chat.
     */
    @GetMapping("/chats/{chatId}")
    public String getChat(@PathVariable int chatId, Model model) {
        try {
            Chat chat = chatService.findChatById(chatId);
            Benutzer seller = chat.getPlant().getSeller();
            Benutzer possibleBuyer = chat.getPossibleBuyer();
            Benutzer currentUser = userService.getCurrentUser();
            if(! (currentUser.equals(possibleBuyer) || currentUser.equals(seller))){
                logger.error("Benutzer ist nicht berechtigt, die Nachrichten in diesen Chat zu sehen.");
                model.addAttribute("error", "Sie sind nicht berechtigt, die Nachrichten in diesen Chat zu sehen, da sie nicht Teilnehmer dieses Chats sind.");
                return "error";
            }
            model.addAttribute("allMessages", chat.getMessages());
            return "messages";
        } catch (Exception findChatException) {
            logger.error("Fehler beim Abrufen der Nachrichten für Chat mit Id: " + chatId, findChatException);
            model.addAttribute("error", "Fehler beim Abrufen der Nachrichten für Chat mit Id: " + chatId);
            return "error";
        }
    }

    /**
     * Creates a new chat for the specified plant.
     *
     * This method is called when a user clicks the "Send Message" button for a plant.
     * It checks if the current user and the plant are valid, and then creates a new chat
     * between the current user (as a potential buyer) and the seller of the plant.
     *
     * @param plantId The ID of the plant for which a new chat is to be created.
     * @param model The model passed to the view to set error messages or other attributes.
     * @return A redirect to the view of the newly created chat or an error page in case of a problem.
     */
    @PostMapping("/plants/{plantId}/newChat")
    public String createChat(@PathVariable int plantId, Model model) {
        try {
            Benutzer currentBenutzer = userService.getCurrentUser();
            if (currentBenutzer == null) {
                logger.error("Current user not found");
                model.addAttribute("error", "Benutzer nicht gefunden");
                return "error";
            }
            Plant interestedPlant = plantService.findPlantById(plantId);
            if (interestedPlant == null) {
                logger.error("Plant not found for id: " + plantId);
                model.addAttribute("error", "Pflanze nicht gefunden");
                return "error";
            }
            if (interestedPlant.getSeller().getUserId() == currentBenutzer.getUserId()) {
                logger.error("Der Verkäufer dieser Pflanze kann keine Nachrichten an sich selber schicken!");
                model.addAttribute("error", "Der Verkäufer dieser Pflanze kann sich selber keine Nachricht schicken");
                return "error";
            }
            Chat newChat = new Chat();
            newChat.setPossibleBuyer(currentBenutzer);
            newChat.setPlant(interestedPlant);
            chatService.saveChat(newChat);
            return "redirect:/chats/" + newChat.getChatId();  // Nach dem Speichern zur Chat-Ansicht umleiten
        } catch (Exception e) {
            logger.error("Fehler beim Erstellen eines neuen Chats", e);
            model.addAttribute("error", "Fehler beim Erstellen eines neuen Chats");
            return "error";
        }
    }
}