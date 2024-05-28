package com.sopra.pflanzenkleinanzeigen.controller;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//TODO: HomeController wahrscheinlich löschen, da wir direkt die Pflanzen anzeigen möchten.
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//TODO: Sollen wir error page implementieren?
    /**
     * This method shows the home page of your application.
     * @param model contains all ModelAttributes.
     * @return home page.
     */
    @GetMapping("/")
    public String showHome(Model model) {
        try{
        Benutzer currentBenutzer = userService.getCurrentUser();
        model.addAttribute("currentBenutzer", currentBenutzer);
        model.addAttribute("message", "Willkommen " + currentBenutzer.getUsername() + "!");
        } catch (Exception BenutzerException) {
            logger.error("Fehler beim Abrufen des aktuellen Benutzers", BenutzerException);
            model.addAttribute("error", "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
            //return "error"; -> this would be the better solution, but we don't have an error page yet
        }
        return "home";
    }
}
