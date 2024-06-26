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
        normalUser.setFirstname("Peter");
        normalUser.setLastname("Fox");
        normalUser.setEmail("peter@foxmail.com");
        normalUser.setImagePath("/plant-images/pflanze_user1.png");
        userService.saveUser(normalUser);

        Benutzer normalUser2 = new Benutzer();
        normalUser2.setUsername("Pauli");
        normalUser2.setPassword(passwordEncoder.encode("4321"));
        normalUser2.setRoles(userRoles);
        normalUser2.setFirstname("Paul");
        normalUser2.setLastname("Kuhn");
        normalUser2.setEmail("paul.kuhn@gmail.com");
        normalUser2.setImagePath("/plant-images/bild_user2.png");

        userService.saveUser(normalUser2);

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        admin.setFirstname("Marc");
        admin.setLastname("Uwe");
        admin.setEmail("marc.uwe@uni.de");
        admin.setImagePath("/plant-images/bild_user3.png");

        userService.saveUser(admin);

        Benutzer andrea = new Benutzer();
        andrea.setUsername("andrea");
        andrea.setPassword(passwordEncoder.encode("andrea"));
        andrea.setRoles(adminRoles);
        andrea.setFirstname("Andrea");
        andrea.setLastname("Chatzaretta");
        andrea.setEmail("andrea.cha@uni.de");
        andrea.setImagePath("/plant-images/bild_user4.png");
        userService.saveUser(andrea);

        Benutzer natti = new Benutzer();
        natti.setUsername("natti");
        natti.setPassword(passwordEncoder.encode("natti"));
        natti.setRoles(adminRoles);
        natti.setFirstname("Natalie");
        natti.setLastname("Schulz");
        natti.setEmail("natti.sch@uni.de");
        natti.setImagePath("/plant-images/bild_user5.png");
        userService.saveUser(natti);

        Benutzer sena = new Benutzer();
        sena.setUsername("sena");
        sena.setPassword(passwordEncoder.encode("sena"));
        sena.setRoles(adminRoles);
        sena.setFirstname("Sena");
        sena.setLastname("Demiroz");
        sena.setEmail("sena.demi@uni.de");
        sena.setImagePath("/plant-images/bild_user6.png");
        userService.saveUser(sena);

        Benutzer sharon = new Benutzer();
        sharon.setUsername("sharon");
        sharon.setPassword(passwordEncoder.encode("sharon"));
        sharon.setRoles(adminRoles);
        sharon.setFirstname("Sharon");
        sharon.setLastname("Tabot");
        sharon.setEmail("sharon.tabot@uni.de");
        sharon.setImagePath("/plant-images/bild_user7.png");
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
        testVerkauft.setName("Strauß");
        testVerkauft.setPrice(new BigDecimal("15.00"));
        testVerkauft.setHeight(new BigDecimal("50.00"));
        testVerkauft.setDescription("hat viele Farben");
        testVerkauft.setSeller(admin);
        testVerkauft.setBuyer(andrea);
        testVerkauft.setImagePath("/plant-images/mixBlumen.jpg");
        plantService.savePlantDataLoader(testVerkauft);

        Plant testNoBeziehungen = new Plant();
        testNoBeziehungen.setName("Tulpe");
        testNoBeziehungen.setPrice(new BigDecimal("5.00"));
        testNoBeziehungen.setHeight(new BigDecimal("10.00"));
        testNoBeziehungen.setDescription("in rot");
        testNoBeziehungen.setSeller(admin);
        testNoBeziehungen.setImagePath("/plant-images/Tulpen.jpg");
        plantService.savePlantDataLoader(testNoBeziehungen);

        Plant orangenbaum = new Plant();
        orangenbaum.setName("Orangenbaum");
        orangenbaum.setPrice(new BigDecimal("25.00"));
        orangenbaum.setHeight(new BigDecimal("60.00"));
        orangenbaum.setDescription("hat leckere Orangen");
        orangenbaum.setSeller(admin);
        orangenbaum.setImagePath("/plant-images/orangenbaum.JPG");
        plantService.savePlantDataLoader(orangenbaum);

        Plant monstera = new Plant();
        monstera.setName("Monstera");
        monstera.setPrice(new BigDecimal("35.00"));
        monstera.setHeight(new BigDecimal("35.00"));
        monstera.setDescription("hat sehr große Blätter");
        monstera.setSeller(admin);
        monstera.setImagePath("/plant-images/monstera.JPG");
        plantService.savePlantDataLoader(monstera);

        Plant testData1 = new Plant();
        testData1.setName("Kaktus");
        testData1.setPrice(new BigDecimal("15.00"));
        testData1.setHeight(new BigDecimal("40.00"));
        testData1.setDescription("Ist sehr stachelig");
        testData1.setSeller(sena);
        testData1.setImagePath("/plant-images/testData1.JPG");
        plantService.savePlantDataLoader(testData1);

        Plant testData2 = new Plant();
        testData2.setName("Kaktus");
        testData2.setPrice(new BigDecimal("17.00"));
        testData2.setHeight(new BigDecimal("30.00"));
        testData2.setDescription("Er strahlt im einem besonderen Grün");
        testData2.setSeller(sena);
        testData2.setImagePath("/plant-images/testData2.JPG");
        plantService.savePlantDataLoader(testData2);

        Plant testData3 = new Plant();
        testData3.setName("Rose");
        testData3.setPrice(new BigDecimal("23.00"));
        testData3.setHeight(new BigDecimal("10.00"));
        testData3.setDescription("Sie ist rot wie die Liebe und richt sehr gut");
        testData3.setSeller(andrea);
        testData3.setImagePath("/plant-images/testData3.JPG");
        plantService.savePlantDataLoader(testData3);

        Plant testData4 = new Plant();
        testData4.setName("Zitronenbaum");
        testData4.setPrice(new BigDecimal("65.00"));
        testData4.setHeight(new BigDecimal("60.00"));
        testData4.setDescription("Hat immer sehr viele Früchte");
        testData4.setSeller(sena);
        testData4.setImagePath("/plant-images/testData4.JPG");
        plantService.savePlantDataLoader(testData4);

        Plant testData5 = new Plant();
        testData5.setName("Elephantenohr");
        testData5.setPrice(new BigDecimal("45.00"));
        testData5.setHeight(new BigDecimal("33.00"));
        testData5.setDescription("Hat sehr sehr große Blätter");
        testData5.setSeller(sharon);
        testData5.setImagePath("/plant-images/testData5.JPG");
        plantService.savePlantDataLoader(testData5);

        Plant testData6 = new Plant();
        testData6.setName("Rundkaktus");
        testData6.setPrice(new BigDecimal("15.00"));
        testData6.setHeight(new BigDecimal("22.00"));
        testData6.setDescription(" Ist nicht sehr groß ");
        testData6.setSeller(natti);
        testData6.setImagePath("/plant-images/testData6.JPG");
        plantService.savePlantDataLoader(testData6);

        Plant testData7 = new Plant();
        testData7.setName("Narzissen");
        testData7.setPrice(new BigDecimal("10.00"));
        testData7.setHeight(new BigDecimal("4.00"));
        testData7.setDescription("Strahlen wie die Sonne");
        testData7.setSeller(sena);
        testData7.setImagePath("/plant-images/testData7.JPG");
        plantService.savePlantDataLoader(testData7);

        Plant testData8 = new Plant();
        testData8.setName("Grasstrauch");
        testData8.setPrice(new BigDecimal("13.00"));
        testData8.setHeight(new BigDecimal("30.00"));
        testData8.setDescription("Gibt einen guten Geruch");
        testData8.setSeller(natti);
        testData8.setImagePath("/plant-images/Graspflanze.jpeg");
        plantService.savePlantDataLoader(testData8);

        Plant testData9 = new Plant();
        testData9.setName("Wasserrose");
        testData9.setPrice(new BigDecimal("13.00"));
        testData9.setHeight(new BigDecimal("30.00"));
        testData9.setDescription("Muss nicht nur immer Wasser gehalten werden");
        testData9.setSeller(sena);
        testData9.setImagePath("/plant-images/testData9.JPG");
        plantService.savePlantDataLoader(testData9);

        Plant testData10 = new Plant();
        testData10.setName("Flora");
        testData10.setPrice(new BigDecimal("5.00"));
        testData10.setHeight(new BigDecimal("6.00"));
        testData10.setDescription("Wir nur sehr klein und kommt ohne Wasser zurecht");
        testData10.setSeller(sena);
        testData10.setImagePath("/plant-images/testData10.JPG");
        plantService.savePlantDataLoader(testData10);

        Plant testData11 = new Plant();
        testData11.setName("Kaktussammlung");
        testData11.setPrice(new BigDecimal("11.00"));
        testData11.setHeight(new BigDecimal("32.00"));
        testData11.setDescription("Sammlung verschiedner Kaktus Arten");
        testData11.setSeller(sena);
        testData11.setImagePath("/plant-images/testData11.JPG");
        plantService.savePlantDataLoader(testData11);

        Plant testData12 = new Plant();
        testData12.setName("Gummibaum");
        testData12.setPrice(new BigDecimal("12.00"));
        testData12.setHeight(new BigDecimal("24.00"));
        testData12.setDescription("Sehr schön");
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
        message1.setMessageContent("Hallo");
        message1.setSentAt(anInstant1);
        messageService.saveMessage(message1);

        Message message2 = new Message();
        message2.setChat(chat1);
        message2.setSender(normalUser);
        message2.setMessageContent("Ich möchte deine Pflanze gerne kaufen");
        message2.setSentAt(anInstant2);
        messageService.saveMessage(message2);

        Message message3 = new Message();
        message3.setChat(chat2);
        message3.setSender(normalUser);
        message3.setMessageContent("Hallo");
        message3.setSentAt(anInstant3);
        messageService.saveMessage(message3);

        Message message4 = new Message();
        message4.setChat(chat1);
        message4.setSender(normalUser);
        message4.setMessageContent("Sharon war hier!");
        message4.setSentAt(anInstant2);
        messageService.saveMessage(message4);

        Message message5 = new Message();
        message5.setChat(chat1);
        message5.setSender(normalUser);
        message5.setMessageContent("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        message5.setSentAt(anInstant2);
        messageService.saveMessage(message5);

        Message message6 = new Message();
        message6.setChat(chat1);
        message6.setSender(normalUser);
        message6.setMessageContent("Sharon war hier!");
        message6.setSentAt(anInstant2);
        messageService.saveMessage(message6);

        Message message7 = new Message();
        message7.setChat(chat1);
        message7.setSender(normalUser);
        message7.setMessageContent("Sharon war hier!");
        message7.setSentAt(anInstant2);
        messageService.saveMessage(message7);

        Message message8 = new Message();
        message8.setChat(chat1);
        message8.setSender(normalUser);
        message8.setMessageContent("Sharon war hier!");
        message8.setSentAt(anInstant2);
        messageService.saveMessage(message8);

        Message message9 = new Message();
        message9.setChat(chat1);
        message9.setSender(normalUser);
        message9.setMessageContent("Sharon war hier!");
        message9.setSentAt(anInstant2);
        messageService.saveMessage(message9);

        Message message10 = new Message();
        message10.setChat(chat1);
        message10.setSender(normalUser);
        message10.setMessageContent("Sharon war hier!");
        message10.setSentAt(anInstant2);
        messageService.saveMessage(message10);

        Message message11 = new Message();
        message11.setChat(chat1);
        message11.setSender(normalUser);
        message11.setMessageContent("Sharon war hier!");
        message11.setSentAt(anInstant2);
        messageService.saveMessage(message11);

        Message message12 = new Message();
        message12.setChat(chat1);
        message12.setSender(normalUser);
        message12.setMessageContent("Sharon war hier!");
        message12.setSentAt(anInstant2);
        messageService.saveMessage(message12);

        Message message13 = new Message();
        message13.setChat(chat1);
        message13.setSender(normalUser);
        message13.setMessageContent("Sharon war hier!");
        message13.setSentAt(anInstant2);
        messageService.saveMessage(message13);

        Message message14 = new Message();
        message14.setChat(chat1);
        message14.setSender(normalUser);
        message14.setMessageContent("Sharon war hier!");
        message14.setSentAt(anInstant2);
        messageService.saveMessage(message14);

        Message message15 = new Message();
        message15.setChat(chat1);
        message15.setSender(normalUser);
        message15.setMessageContent("Sharon war hier!");
        message15.setSentAt(anInstant2);
        messageService.saveMessage(message15);

        Message message16 = new Message();
        message16.setChat(chat1);
        message16.setSender(normalUser);
        message16.setMessageContent("Sharon war hier!");
        message16.setSentAt(anInstant2);
        messageService.saveMessage(message16);

        Message message17 = new Message();
        message17.setChat(chat1);
        message17.setSender(normalUser);
        message17.setMessageContent("Sharon war hier!");
        message17.setSentAt(anInstant2);
        messageService.saveMessage(message17);


        Message message18 = new Message();
        message18.setChat(chat1);
        message18.setSender(normalUser);
        message18.setMessageContent("Sharon war hier!");
        message18.setSentAt(anInstant2);
        messageService.saveMessage(message18);

        Message message19 = new Message();
        message19.setChat(chat1);
        message19.setSender(normalUser);
        message19.setMessageContent("Sharon war hier!");
        message19.setSentAt(anInstant2);
        messageService.saveMessage(message19);

        Message message20 = new Message();
        message20.setChat(chat1);
        message20.setSender(normalUser);
        message20.setMessageContent("Sharon war hier!");
        message20.setSentAt(anInstant2);
        messageService.saveMessage(message20);

        Message message21 = new Message();
        message21.setChat(chat1);
        message21.setSender(normalUser);
        message21.setMessageContent("Sharon war hier!");
        message21.setSentAt(anInstant2);
        messageService.saveMessage(message21);

        Message message22 = new Message();
        message22.setChat(chat1);
        message22.setSender(normalUser);
        message22.setMessageContent("Sharon war hier!");
        message22.setSentAt(anInstant2);
        messageService.saveMessage(message22);

        Message message23 = new Message();
        message23.setChat(chat1);
        message23.setSender(normalUser);
        message23.setMessageContent("Sharon war hier!");
        message23.setSentAt(anInstant2);
        messageService.saveMessage(message23);

        Message message24 = new Message();
        message24.setChat(chat1);
        message24.setSender(normalUser);
        message24.setMessageContent("Sharon war hier!");
        message24.setSentAt(anInstant2);
        messageService.saveMessage(message24);

        Message message25 = new Message();
        message25.setChat(chat1);
        message25.setSender(normalUser);
        message25.setMessageContent("Sharon war hier!");
        message25.setSentAt(anInstant2);
        messageService.saveMessage(message25);

        Message message26 = new Message();
        message26.setChat(chat1);
        message26.setSender(normalUser);
        message26.setMessageContent("Sharon war hier!");
        message26.setSentAt(anInstant2);
        messageService.saveMessage(message26);

        Message message27 = new Message();
        message27.setChat(chat1);
        message27.setSender(normalUser);
        message27.setMessageContent("Sharon war hier!");
        message27.setSentAt(anInstant2);
        messageService.saveMessage(message27);

        Message message28 = new Message();
        message28.setChat(chat1);
        message28.setSender(normalUser);
        message28.setMessageContent("Sharon war hier!");
        message28.setSentAt(anInstant2);
        messageService.saveMessage(message28);

        Message message29 = new Message();
        message29.setChat(chat1);
        message29.setSender(normalUser);
        message29.setMessageContent("Sharon war hier!");
        message29.setSentAt(anInstant2);
        messageService.saveMessage(message29);

        Message message30 = new Message();
        message30.setChat(chat1);
        message30.setSender(normalUser);
        message30.setMessageContent("Sharon war hier!");
        message30.setSentAt(anInstant2);
        messageService.saveMessage(message30);

        Message message31 = new Message();
        message31.setChat(chat1);
        message31.setSender(normalUser);
        message31.setMessageContent("Sharon war hier!");
        message31.setSentAt(anInstant2);
        messageService.saveMessage(message31);
    }
}