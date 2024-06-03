package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.entity.Message;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.MessageService;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.List;

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

    @Autowired
    private MessageService messageService;

    /**
     * This method retrieves all chats for the currently logged-in user.
     * It adds these chats to the model under the attribute "allChats".
     * The method then returns the view "chats" which displays these chats.
     *
     * @param model The model that is sent to the view.
     * @return "chats", the view with all chats from the current user.
     */
    @GetMapping("/chats")
    public String getChats(Model model) {
        Benutzer currentBenutzer = userService.getCurrentUser();
        model.addAttribute("allChats", chatService.findUserChats(currentBenutzer.getUserId()));
        return "chats";
    }

    /**
     * This method retrieves the messages for a specific chat.
     * It checks if the current user is authorized to view the chat.
     * If authorized, it adds the chat and its messages to the model and returns the "messages" view.
     * Otherwise, it logs an error and returns an error view with an appropriate message.
     *
     * @param chatId The ID of the chat whose messages should be displayed.
     * @param model The model that is sent to the view.
     * @return "messages", the view with all messages in a specific chat.
     */
    @GetMapping("/chats/{chatId}")
    public String getChat(@PathVariable int chatId, Model model) {
        try {
            Chat chat = chatService.findChatById(chatId);
            Benutzer currentUser = userService.getCurrentUser();
            if (userIsNotAuthorized(chat, currentUser)) {
                logger.error("Der Benutzer ist nicht berechtigt, Nachrichten in diesem Chat zu sehen.");
                model.addAttribute("error", "Sie sind nicht berechtigt, Nachrichten in diesem Chat zu sehen, da Sie kein Teilnehmer sind.");
                return "error";
            }
            model.addAttribute("chat", chat);
            model.addAttribute("allMessages", chat.getMessages());
            model.addAttribute("newMessage", new Message());
            return "messages";
        } catch (Exception cannotGetChatException) {
            logger.error("Fehler beim Abrufen von Nachrichten für den Chat mit der ID: " + chatId, cannotGetChatException);
            model.addAttribute("error", "Fehler beim Abrufen von Nachrichten für den Chat mit der ID: " + chatId);
            return "error";
        }
    }

    /**
     * This helper method checks if the current user is authorized to view the chat.
     * It compares the current user with the seller and potential buyer of the plant in the chat.
     *
     * @param chat The chat object.
     * @param currentUser The current user.
     * @return True if the user is authorized, false otherwise.
     */
    private boolean userIsNotAuthorized(Chat chat, Benutzer currentUser) {
        Benutzer seller = chat.getPlant().getSeller();
        Benutzer possibleBuyer = chat.getPossibleBuyer();
        return !currentUser.equals(possibleBuyer) && !currentUser.equals(seller);
    }

    /**
     * This method handles the creation of a new chat for a specific plant.
     * It first checks if the current user is the seller of the plant and prevents self-chat.
     * If a chat already exists between the user and the plant, it redirects to that chat.
     * Otherwise, it creates a new chat, saves it, and redirects to the new chat view.
     *
     * @param plantId The ID of the plant for which a new chat is to be created.
     * @param model The model passed to the view to set error messages or other attributes.
     * @return A redirect to the view of the newly created chat or an error page in case of a problem.
     */
    @PostMapping("/plants/{plantId}/newChat")
    public String createChat(@PathVariable int plantId, @Valid @ModelAttribute("newChat") Chat newChat, BindingResult result, Model model) {
        try {
            Benutzer currentBenutzer = userService.getCurrentUser();
            Plant interestedPlant = plantService.findPlantById(plantId);
            if (interestedPlant.getSeller().equals((currentBenutzer))) {
                logger.error("Der Verkäufer dieser Pflanze kann sich selbst keine Nachrichten senden!");
                model.addAttribute("error", "Der Verkäufer dieser Pflanze kann sich selbst keine Nachrichten senden!");
                return "error";
            }

            Chat existingChat = findExistingChat(currentBenutzer, interestedPlant);
            if (existingChat != null) {
                return "redirect:/chats/" + existingChat.getChatId();
            }

            newChat.setPossibleBuyer(currentBenutzer);
            newChat.setPlant(interestedPlant);
            chatService.saveChat(newChat);
            return "redirect:/chats/" + newChat.getChatId();
        } catch (Exception cannotCreateChatException) {
            logger.error("Fehler beim Erstellen eines neuen Chats", cannotCreateChatException);
            model.addAttribute("error", "Fehler beim Erstellen eines neuen Chats");
            return "error";
        }
    }

    /**
     * This helper method finds an existing chat between the current user and the seller of the specified plant.
     * It iterates through all chats and checks if there is a match with the current user and the plant.
     *
     * @param currentBenutzer The current user.
     * @param interestedPlant The plant the user is interested in.
     * @return The existing chat if found, null otherwise.
     */
    private Chat findExistingChat(Benutzer currentBenutzer, Plant interestedPlant) {
        List<Chat> allChats = chatService.findAllChats();
        for (Chat chat : allChats) {
            if (chat.getPossibleBuyer().equals(currentBenutzer) && chat.getPlant().equals(interestedPlant)) {
                return chat;
            }
        }
        return null;
    }

    /**
     * This method handles sending a new message in a chat.
     * It retrieves the chat and checks if the current user is authorized to send a message.
     * If authorized and there are no validation errors, it saves the new message and redirects to the chat view.
     * Otherwise, it logs an error and returns an error view with an appropriate message.
     *
     * @param chatId The ID of the chat where the message is being sent.
     * @param newMessage The new message being sent.
     * @param result BindingResult object that holds validation errors.
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view to be rendered.
     */
    @PostMapping("/chats/{chatId}")
    public String sendMessage(@PathVariable int chatId, @Valid @ModelAttribute("newMessage") Message newMessage, BindingResult result, Model model) {
        try {
            Chat chat = chatService.findChatById(chatId);
            if (userIsNotAuthorized(chat, userService.getCurrentUser())) {
                logger.error("Der Benutzer ist nicht berechtigt, Nachrichten in diesem Chat zu senden.");
                model.addAttribute("error", "Sie sind nicht berechtigt, Nachrichten in diesem Chat zu senden.");
                return "error";
            }

            newMessage.setChat(chat);
            newMessage.setSender(userService.getCurrentUser());
            if (result.hasErrors()) {
                model.addAttribute("newMessage", new Message());
                return "messages";
            }

            newMessage.setSentAt(Instant.now());
            messageService.saveMessage(newMessage);
            return "redirect:/chats/" + chatId;
        } catch (Exception cannotCreateMessageException){
            logger.error("Fehler beim Senden einer neuen Nachricht", cannotCreateMessageException);
            model.addAttribute("error", "Fehler beim Senden einer neuen Nachricht");
            return "error";
        }
    }
}