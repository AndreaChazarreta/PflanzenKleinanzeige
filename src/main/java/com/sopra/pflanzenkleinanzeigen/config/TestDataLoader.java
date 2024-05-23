package com.sopra.pflanzenkleinanzeigen.config;

import com.sopra.pflanzenkleinanzeigen.entity.Chat;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.service.ChatService;
import com.sopra.pflanzenkleinanzeigen.service.PlantService;
import com.sopra.pflanzenkleinanzeigen.service.RoleService;
import com.sopra.pflanzenkleinanzeigen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlantService plantService;

    @Autowired
    private ChatService chatService;

    /**
     * Diese Methode wird zum Aufsetzen von Testdaten für die Datenbank verwendet werden. Die Methode wird immer dann
     * ausgeführt, wenn der Spring Kontext initialisiert wurde, d.h. wenn Sie Ihren Server (neu-)starten.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initialisiere Datenbank mit Testdaten...");

        // Initialisieren Sie Beispielobjekte und speichern Sie diese über Ihre Services
        Rolle userRole = new Rolle("ROLE_USER");
        Rolle adminRole = new Rolle("ROLE_ADMIN");
        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);

        Set<Rolle> userRoles = new HashSet<>();
        userRoles.add(userRole);

        Set<Rolle> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        Benutzer normalUser = new Benutzer();
        normalUser.setUsername("user");
        normalUser.setPassword(passwordEncoder.encode("1234"));
        normalUser.setRoles(userRoles);
        userService.saveUser(normalUser);

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userService.saveUser(admin);

        Benutzer andrea = new Benutzer();
        andrea.setUsername("andrea");
        andrea.setPassword(passwordEncoder.encode("admin"));
        andrea.setRoles(adminRoles);
        userService.saveUser(andrea);

        Benutzer natti = new Benutzer();
        natti.setUsername("natti");
        natti.setPassword(passwordEncoder.encode("admin"));
        natti.setRoles(adminRoles);
        userService.saveUser(natti);

        Benutzer sena = new Benutzer();
        sena.setUsername("sena");
        sena.setPassword(passwordEncoder.encode("admin"));
        sena.setRoles(adminRoles);
        userService.saveUser(sena);

        Benutzer sharon = new Benutzer();
        sharon.setUsername("sharon");
        sharon.setPassword(passwordEncoder.encode("admin"));
        sharon.setRoles(adminRoles);
        userService.saveUser(sharon);

        Plant kaktus = new Plant();
        kaktus.setName("Kaktus");
        kaktus.setPrice(12.45);
        kaktus.setHeight(34.09);
        kaktus.setDescription("sehr schön");
        kaktus.setSeller(admin);
        plantService.savePlant(kaktus);

        Chat chat1 = new Chat();
        chat1.setPlant(kaktus);
        chat1.setPossibleBuyer(normalUser);
        chatService.saveChat(chat1);
    }
}