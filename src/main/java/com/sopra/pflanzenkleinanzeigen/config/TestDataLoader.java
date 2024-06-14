package com.sopra.pflanzenkleinanzeigen.config;

import com.sopra.pflanzenkleinanzeigen.entity.*;
import com.sopra.pflanzenkleinanzeigen.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to load test data into the database.
 * It implements ApplicationListener, which allows it to run when the application context is refreshed.
 */
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

    @Autowired
    private MessageService messageService;

    /**
     * This method is triggered when the application context is refreshed.
     * It initializes the database with test data.
     *
     * @param event The event that triggers this method.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initialize database with test data...");

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

        Benutzer normalUser2 = new Benutzer();
        normalUser2.setUsername("1");
        normalUser2.setPassword(passwordEncoder.encode("1"));
        normalUser2.setRoles(userRoles);
        normalUser2.setFirstname("Paul");
        normalUser2.setLastname("Kuhn");
        normalUser2.setEmail("paul.kuhn@gmail.com");
        userService.saveUser(normalUser2);

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        admin.setFirstname("Marc");
        admin.setLastname("Uwe");
        admin.setEmail("marc.uwe@uni.de");
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
        kaktus.setPrice(new BigDecimal("12.45"));
        kaktus.setHeight(new BigDecimal("34.09"));
        kaktus.setDescription("sehr schön");
        kaktus.setSeller(admin);
        kaktus.setImagePath("/plant-images/kaktus1.JPG");
        plantService.savePlantDataLoader(kaktus);

        Plant rose = new Plant();
        rose.setName("Rose");
        rose.setPrice(new BigDecimal("15.00"));
        rose.setHeight(new BigDecimal("50.00"));
        rose.setDescription("wunderschön");
        rose.setSeller(andrea);
        rose.setImagePath("/plant-images/rose1.JPG");
        plantService.savePlantDataLoader(rose);

        Plant testVerkauft = new Plant();
        testVerkauft.setName("TestVerkauft");
        testVerkauft.setPrice(new BigDecimal("15.00"));
        testVerkauft.setHeight(new BigDecimal("50.00"));
        testVerkauft.setDescription("wunderschön");
        testVerkauft.setSeller(admin);
        testVerkauft.setBuyer(andrea);
        testVerkauft.setImagePath("/plant-images/mixBlumen.jpg");
        plantService.savePlantDataLoader(testVerkauft);

        Plant testNoBeziehungen = new Plant();
        testNoBeziehungen.setName("testNoBeziehungen");
        testNoBeziehungen.setPrice(new BigDecimal("15.00"));
        testNoBeziehungen.setHeight(new BigDecimal("50.00"));
        testNoBeziehungen.setDescription("wunderschön");
        testNoBeziehungen.setSeller(admin);
        testNoBeziehungen.setImagePath("/plant-images/noImage.jpg");
        plantService.savePlantDataLoader(testNoBeziehungen);

        Plant orangenbaum = new Plant();
        orangenbaum.setName("orangenbaum");
        orangenbaum.setPrice(new BigDecimal("15.00"));
        orangenbaum.setHeight(new BigDecimal("50.00"));
        orangenbaum.setDescription("hat leckere Orangen");
        orangenbaum.setSeller(admin);
        orangenbaum.setImagePath("/plant-images/orangenbaum.JPG");
        plantService.savePlantDataLoader(orangenbaum);

        Plant monstera = new Plant();
        monstera.setName("monstera");
        monstera.setPrice(new BigDecimal("15.00"));
        monstera.setHeight(new BigDecimal("50.00"));
        monstera.setDescription("hat leckere Orangen");
        monstera.setSeller(admin);
        monstera.setImagePath("/plant-images/monstera.JPG");
        plantService.savePlantDataLoader(monstera);

        Plant testData1 = new Plant();
        testData1.setName("testData1");
        testData1.setPrice(new BigDecimal("15.00"));
        testData1.setHeight(new BigDecimal("50.00"));
        testData1.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData1.setSeller(sena);
        testData1.setImagePath("/plant-images/testData1.JPG");
        plantService.savePlantDataLoader(testData1);

        Plant testData2 = new Plant();
        testData2.setName("testData2");
        testData2.setPrice(new BigDecimal("15.00"));
        testData2.setHeight(new BigDecimal("50.00"));
        testData2.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData2.setSeller(sena);
        testData2.setImagePath("/plant-images/testData2.JPG");
        plantService.savePlantDataLoader(testData2);

        Plant testData3 = new Plant();
        testData3.setName("testData3");
        testData3.setPrice(new BigDecimal("15.00"));
        testData3.setHeight(new BigDecimal("50.00"));
        testData3.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData3.setSeller(andrea);
        testData3.setImagePath("/plant-images/testData3.JPG");
        plantService.savePlantDataLoader(testData3);

        Plant testData4 = new Plant();
        testData4.setName("testData4");
        testData4.setPrice(new BigDecimal("15.00"));
        testData4.setHeight(new BigDecimal("50.00"));
        testData4.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData4.setSeller(sena);
        testData4.setImagePath("/plant-images/testData4.JPG");
        plantService.savePlantDataLoader(testData4);

        Plant testData5 = new Plant();
        testData5.setName("testData5");
        testData5.setPrice(new BigDecimal("15.00"));
        testData5.setHeight(new BigDecimal("50.00"));
        testData5.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData5.setSeller(sharon);
        testData5.setImagePath("/plant-images/testData5.JPG");
        plantService.savePlantDataLoader(testData5);

        Plant testData6 = new Plant();
        testData6.setName("testData6");
        testData6.setPrice(new BigDecimal("15.00"));
        testData6.setHeight(new BigDecimal("50.00"));
        testData6.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData6.setSeller(natti);
        testData6.setImagePath("/plant-images/testData6.JPG");
        plantService.savePlantDataLoader(testData6);

        Plant testData7 = new Plant();
        testData7.setName("testData7");
        testData7.setPrice(new BigDecimal("15.00"));
        testData7.setHeight(new BigDecimal("50.00"));
        testData7.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData7.setSeller(sena);
        testData7.setImagePath("/plant-images/testData7.JPG");
        plantService.savePlantDataLoader(testData7);

        Plant testData8 = new Plant();
        testData8.setName("testData8");
        testData8.setPrice(new BigDecimal("15.00"));
        testData8.setHeight(new BigDecimal("50.00"));
        testData8.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData8.setSeller(natti);
        testData8.setImagePath("/plant-images/testData8.JPG");
        plantService.savePlantDataLoader(testData4);

        Plant testData9 = new Plant();
        testData9.setName("testData9");
        testData9.setPrice(new BigDecimal("15.00"));
        testData9.setHeight(new BigDecimal("50.00"));
        testData9.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData9.setSeller(sena);
        testData9.setImagePath("/plant-images/testData9.JPG");
        plantService.savePlantDataLoader(testData9);

        Plant testData10 = new Plant();
        testData10.setName("testData10");
        testData10.setPrice(new BigDecimal("15.00"));
        testData10.setHeight(new BigDecimal("50.00"));
        testData10.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData10.setSeller(sena);
        testData10.setImagePath("/plant-images/testData10.JPG");
        plantService.savePlantDataLoader(testData10);

        Plant testData11 = new Plant();
        testData11.setName("testData11");
        testData11.setPrice(new BigDecimal("15.00"));
        testData11.setHeight(new BigDecimal("50.00"));
        testData11.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData11.setSeller(sena);
        testData11.setImagePath("/plant-images/testData11.JPG");
        plantService.savePlantDataLoader(testData11);

        Plant testData12 = new Plant();
        testData12.setName("testData12");
        testData12.setPrice(new BigDecimal("15.00"));
        testData12.setHeight(new BigDecimal("50.00"));
        testData12.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
        testData12.setSeller(sharon);
        testData12.setImagePath("/plant-images/testData12.JPG");
        plantService.savePlantDataLoader(testData12);

        Chat chat1 = new Chat();
        chat1.setPlant(kaktus);
        chat1.setPossibleBuyer(normalUser);
        chatService.saveChat(chat1);

        Chat chat2 = new Chat();
        chat2.setPlant(rose);
        chat2.setPossibleBuyer(normalUser);
        chatService.saveChat(chat2);

        Instant anInstant1 = Instant.ofEpochSecond(1715324919);
        Instant anInstant2 = Instant.ofEpochSecond(1715843319);
        Instant anInstant3 = Instant.ofEpochSecond(1716188919);

        Message message1 = new Message();
        message1.setChat(chat1);
        message1.setSender(normalUser);
        message1.setMessageContent("Hello World!");
        message1.setSentAt(anInstant1);
        messageService.saveMessage(message1);

        Message message2 = new Message();
        message2.setChat(chat1);
        message2.setSender(normalUser);
        message2.setMessageContent("Sharon war hier!");
        message2.setSentAt(anInstant2);
        messageService.saveMessage(message2);

        Message message3 = new Message();
        message3.setChat(chat2);
        message3.setSender(normalUser);
        message3.setMessageContent("Andrea war hier!");
        message3.setSentAt(anInstant3);
        messageService.saveMessage(message3);
    }
}