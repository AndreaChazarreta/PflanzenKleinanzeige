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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CareTipService careTipService;

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
        andrea.setLastname("Chazarreta");
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

        Category category1 = new Category();
        category1.setName("Baum");
        categoryService.saveCategory(category1);

        Category category2 = new Category();
        category2.setName("Blume");
        categoryService.saveCategory(category2);

        Category category3 = new Category();
        category3.setName("Busch");
        categoryService.saveCategory(category3);

        Category category4 = new Category();
        category4.setName("Gräser");
        categoryService.saveCategory(category4);

        Category category5 = new Category();
        category5.setName("Kräuter");
        categoryService.saveCategory(category5);

        Category category6 = new Category();
        category6.setName("Gemüse");
        categoryService.saveCategory(category6);

        Category category7 = new Category();
        category7.setName("Sonstiges");
        categoryService.saveCategory(category7);

        CareTip careTip1 = new CareTip();
        careTip1.setCategory(category2);
        careTip1.setPlantName("Rose");
        careTip1.setLightingConditions("Mittel");
        careTip1.setFertilization("Wenig");
        careTip1.setIrrigation("Viel");
        careTip1.setOtherTips("Die Pflanze ist ehrer für erfahrene Gärtner geeignet. Sie sollt nach jeder Blühzeit beschnitten werden. Zu beachten ist das sie Dornen hat.");
        careTip1.setRepotting("Ja, nach 4 Wochen");
        careTip1.setTemperature("20");
        careTip1.setPlanting("Kann direkt in die Erde");
        careTipService.saveCareTip(careTip1);

        CareTip careTip2 = new CareTip();
        careTip2.setCategory(category3);
        careTip2.setPlantName("Kaktus");
        careTip2.setLightingConditions("Mittel");
        careTip2.setFertilization("Wenig");
        careTip2.setIrrigation("Wenig");
        careTip2.setOtherTips("Die Pflanze ist sehr gut für Anfänger geeignet, da sie auch wenn man sie vergisst nicht gleich eingeht. Außerdem ist zu beachten das sie stacheln hat. ");
        careTip2.setRepotting("Nach 5 Wochen");
        careTip2.setTemperature("15");
        careTip2.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip2);

        CareTip careTip3 = new CareTip();
        careTip3.setCategory(category1);
        careTip3.setPlantName("Eiche");
        careTip3.setLightingConditions("Viel");
        careTip3.setFertilization("Mittel");
        careTip3.setIrrigation("Mittel");
        careTip3.setOtherTips("Die Eiche benötigt viel Platz, um gut zu wachsen.");
        careTip3.setRepotting("Nicht nötig");
        careTip3.setTemperature("10");
        careTip3.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip3);

        CareTip careTip4 = new CareTip();
        careTip4.setCategory(category1);
        careTip4.setPlantName("Obstbaum");
        careTip4.setLightingConditions("Viel");
        careTip4.setFertilization("Viel");
        careTip4.setIrrigation("Wenig");
        careTip4.setOtherTips("Ein Rückschnitt im Frühjahr fördert die Fruchtbildung. Im Sommer am besten ein Netz drüber werfen, damit Vögel die Früchte nicht fressen.");
        careTip4.setRepotting("Nach 6 Monaten");
        careTip4.setTemperature("20");
        careTip4.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip4);

        CareTip careTip5 = new CareTip();
        careTip5.setCategory(category3);
        careTip5.setPlantName("Lavendel");
        careTip5.setLightingConditions("Viel");
        careTip5.setFertilization("Wenig");
        careTip5.setIrrigation("Wenig");
        careTip5.setOtherTips("Lavendel zieht Bienen an und verbreitet einen angenehmen Duft.");
        careTip5.setRepotting("Nach 6 Monaten");
        careTip5.setTemperature("15");
        careTip5.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip5);

        CareTip careTip6 = new CareTip();
        careTip6.setCategory(category4);
        careTip6.setPlantName("Zitronengras");
        careTip6.setLightingConditions("Mittel");
        careTip6.setFertilization("Mittel");
        careTip6.setIrrigation("Viel");
        careTip6.setOtherTips("Zitronengras kann in der Küche vielseitig verwendet werden.");
        careTip6.setRepotting("Nach 2 Wochen");
        careTip6.setTemperature("20");
        careTip6.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip6);

        CareTip careTip7 = new CareTip();
        careTip7.setCategory(category5);
        careTip7.setPlantName("Basilikum");
        careTip7.setLightingConditions("Mittel");
        careTip7.setFertilization("Mittel");
        careTip7.setIrrigation("Mittel");
        careTip7.setOtherTips("Basilikum sollte regelmäßig geerntet werden, um neues Wachstum zu fördern.");
        careTip7.setRepotting("Nach 6 Monaten");
        careTip7.setTemperature("20");
        careTip7.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip7);

        CareTip careTip8 = new CareTip();
        careTip8.setCategory(category6);
        careTip8.setPlantName("Tomate");
        careTip8.setLightingConditions("Viel");
        careTip8.setFertilization("Viel");
        careTip8.setIrrigation("Viel");
        careTip8.setOtherTips("Tomaten brauchen Stützen, um die Frucht zu tragen.");
        careTip8.setRepotting("Nach 6 Monaten");
        careTip8.setTemperature("22");
        careTip8.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip8);

        CareTip careTip9 = new CareTip();
        careTip9.setCategory(category7);
        careTip9.setPlantName("Aloe Vera");
        careTip9.setLightingConditions("Viel");
        careTip9.setFertilization("Wenig");
        careTip9.setIrrigation("Wenig");
        careTip9.setOtherTips("Aloe Vera ist pflegeleicht und hat heilende Eigenschaften.");
        careTip9.setRepotting("Nach 6 Monaten");
        careTip9.setTemperature("20");
        careTip9.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip9);

        CareTip careTip10 = new CareTip();
        careTip10.setCategory(category1);
        careTip10.setPlantName("Birke");
        careTip10.setLightingConditions("Viel");
        careTip10.setFertilization("Mittel");
        careTip10.setIrrigation("Mittel");
        careTip10.setOtherTips("Die Birke ist sehr robust und anpassungsfähig.");
        careTip10.setRepotting("Nach 2 Wochen");
        careTip10.setTemperature("15");
        careTip10.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip10);

        CareTip careTip11 = new CareTip();
        careTip11.setCategory(category2);
        careTip11.setPlantName("Tulpe");
        careTip11.setLightingConditions("Viel");
        careTip11.setFertilization("Mittel");
        careTip11.setIrrigation("Mittel");
        careTip11.setOtherTips("Tulpen sollten im Herbst gepflanzt werden.");
        careTip11.setRepotting("Nach 6 Monaten");
        careTip11.setTemperature("10");
        careTip11.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip11);

        CareTip careTip12 = new CareTip();
        careTip12.setCategory(category3);
        careTip12.setPlantName("Buchsbaum");
        careTip12.setLightingConditions("Mittel");
        careTip12.setFertilization("Mittel");
        careTip12.setIrrigation("Mittel");
        careTip12.setOtherTips("Regelmäßiger Schnitt ist notwendig, um die Form zu halten.");
        careTip12.setRepotting("Nach 6 Monaten");
        careTip12.setTemperature("15");
        careTip12.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip12);

        CareTip careTip13 = new CareTip();
        careTip13.setCategory(category4);
        careTip13.setPlantName("Bambus");
        careTip13.setLightingConditions("Viel");
        careTip13.setFertilization("Viel");
        careTip13.setIrrigation("Viel");
        careTip13.setOtherTips("Bambus wächst sehr schnell und benötigt viel Wasser.");
        careTip13.setRepotting("Nach 6 Monaten");
        careTip13.setTemperature("18");
        careTip13.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip13);

        CareTip careTip14 = new CareTip();
        careTip14.setCategory(category5);
        careTip14.setPlantName("Minze");
        careTip14.setLightingConditions("Mittel");
        careTip14.setFertilization("Mittel");
        careTip14.setIrrigation("Mittel");
        careTip14.setOtherTips("Minze breitet sich schnell aus, daher ist eine Wurzelsperre sinnvoll.");
        careTip14.setRepotting("Nach 6 Monaten");
        careTip14.setTemperature("15");
        careTip14.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip14);

        CareTip careTip15 = new CareTip();
        careTip15.setCategory(category6);
        careTip15.setPlantName("Karotte");
        careTip15.setLightingConditions("Viel");
        careTip15.setFertilization("Mittel");
        careTip15.setIrrigation("Mittel");
        careTip15.setOtherTips("Karotten brauchen lockeren Boden, um gerade zu wachsen.");
        careTip15.setRepotting("Nicht nötig");
        careTip15.setTemperature("15");
        careTip15.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip15);

        CareTip careTip16 = new CareTip();
        careTip16.setCategory(category7);
        careTip16.setPlantName("Orchidee");
        careTip16.setLightingConditions("Mittel");
        careTip16.setFertilization("Wenig");
        careTip16.setIrrigation("Wenig");
        careTip16.setOtherTips("Orchideen brauchen spezielle Erde und einen transparenten Topf.");
        careTip16.setRepotting("Nach 6 Monaten");
        careTip16.setTemperature("20");
        careTip16.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip16);

        CareTip careTip17 = new CareTip();
        careTip17.setCategory(category1);
        careTip17.setPlantName("Tanne");
        careTip17.setLightingConditions("Mittel");
        careTip17.setFertilization("Viel");
        careTip17.setIrrigation("Mittel");
        careTip17.setOtherTips("Wächste schnell und kann sehr groß werden");
        careTip17.setRepotting("Nach 2 Wochen");
        careTip17.setTemperature("10");
        careTip17.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip17);

        CareTip careTip18 = new CareTip();
        careTip18.setCategory(category2);
        careTip18.setPlantName("Sonnenblume");
        careTip18.setLightingConditions("Viel");
        careTip18.setFertilization("Mittel");
        careTip18.setIrrigation("Viel");
        careTip18.setOtherTips("Sonnenblumen drehen sich zum Licht und können sehr hoch wachsen.");
        careTip18.setRepotting("Nicht nötig");
        careTip18.setTemperature("20");
        careTip18.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip18);

        CareTip careTip19 = new CareTip();
        careTip19.setCategory(category3);
        careTip19.setPlantName("Rosmarin");
        careTip19.setLightingConditions("Viel");
        careTip19.setFertilization("Wenig");
        careTip19.setIrrigation("Mittel");
        careTip19.setOtherTips("Rosmarin ist ein sehr widerstandsfähiger Strauch, der wenig Pflege benötigt.");
        careTip19.setRepotting("Nach 6 Monaten");
        careTip19.setTemperature("15");
        careTip19.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip19);

        CareTip careTip20 = new CareTip();
        careTip20.setCategory(category4);
        careTip20.setPlantName("Schilfgras");
        careTip20.setLightingConditions("Mittel");
        careTip20.setFertilization("Mittel");
        careTip20.setIrrigation("Viel");
        careTip20.setOtherTips("Schilfgras eignet sich hervorragend für die Uferbepflanzung.");
        careTip20.setRepotting("Nach 6 Monaten");
        careTip20.setTemperature("18");
        careTip20.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip20);

        CareTip careTip21 = new CareTip();
        careTip21.setCategory(category5);
        careTip21.setPlantName("Petersilie");
        careTip21.setLightingConditions("Mittel");
        careTip21.setFertilization("Mittel");
        careTip21.setIrrigation("Mittel");
        careTip21.setOtherTips("Petersilie ist ein bienenfreundliches Kraut, das regelmäßig geerntet werden sollte.");
        careTip21.setRepotting("Nach 6 Monaten");
        careTip21.setTemperature("15");
        careTip21.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip21);

        CareTip careTip22 = new CareTip();
        careTip22.setCategory(category6);
        careTip22.setPlantName("Salat");
        careTip22.setLightingConditions("Mittel");
        careTip22.setFertilization("Mittel");
        careTip22.setIrrigation("Viel");
        careTip22.setOtherTips("Salat sollte regelmäßig geerntet werden, um neues Wachstum zu fördern.");
        careTip22.setRepotting("Nicht nötig");
        careTip22.setTemperature("18");
        careTip22.setPlanting("Vorzüchten");
        careTipService.saveCareTip(careTip22);

        CareTip careTip24 = new CareTip();
        careTip24.setCategory(category1);
        careTip24.setPlantName("Ahornbaum");
        careTip24.setLightingConditions("Mittel");
        careTip24.setFertilization("Mittel");
        careTip24.setIrrigation("Mittel");
        careTip24.setOtherTips("Ahornbäume sind robust und farbenprächtig im Herbst.");
        careTip24.setRepotting("Nicht nötig");
        careTip24.setTemperature("15");
        careTip24.setPlanting("Direkt in die Erde");
        careTipService.saveCareTip(careTip24);


        Instant anInstantPlant1 = Instant.ofEpochSecond(1717251489);
        Instant anInstantPlant2 = Instant.ofEpochSecond(1717337889);
        Instant anInstantPlant3 = Instant.ofEpochSecond(1717424289);
        Instant anInstantPlant4 = Instant.ofEpochSecond(1717510689);
        Instant anInstantPlant5 = Instant.ofEpochSecond(1717597089);
        Instant anInstantPlant6 = Instant.ofEpochSecond(1717683489);
        Instant anInstantPlant7 = Instant.ofEpochSecond(1717769889);
        Instant anInstantPlant8 = Instant.ofEpochSecond(1717856289);
        Instant anInstantPlant9 = Instant.ofEpochSecond(1717942689);
        Instant anInstantPlant10 = Instant.ofEpochSecond(1718029089);
        Instant anInstantPlant11 = Instant.ofEpochSecond(1718115489);
        Instant anInstantPlant12 = Instant.ofEpochSecond(1718201889);

        Plant kaktus = new Plant();
        kaktus.setName("Kaktus");
        kaktus.setPrice(new BigDecimal("12.45"));
        kaktus.setHeight(new BigDecimal("34.09"));
        kaktus.setDescription("sehr schön");
        kaktus.setSeller(admin);
        kaktus.setImagePath("/plant-images/kaktus1.JPG");
        kaktus.setCategory(category3);
        kaktus.setCareTip(careTip2);
        kaktus.setPotIncluded(true);
        kaktus.setBuyer(andrea);
        kaktus.setSeed(true);
        kaktus.setLifespan("3");
        kaktus.setColor("green");
        plantService.savePlantDataLoader(kaktus);

        Plant rose = new Plant();
        rose.setName("Rose");
        rose.setPrice(new BigDecimal("15.00"));
        rose.setHeight(new BigDecimal("50.00"));
        rose.setDescription("wunderschön");
        rose.setSeller(andrea);
        rose.setImagePath("/plant-images/rose1.JPG");
        rose.setCategory(category2);
        rose.setCareTip(careTip1);
        rose.setPotIncluded(true);
        rose.setCreatedAt(anInstantPlant1);
        plantService.savePlantDataLoader(rose);

        Plant testVerkauft = new Plant();
        testVerkauft.setName("Strauß");
        testVerkauft.setPrice(new BigDecimal("15.00"));
        testVerkauft.setHeight(new BigDecimal("50.00"));
        testVerkauft.setDescription("hat viele Farben");
        testVerkauft.setSeller(admin);
        testVerkauft.setBuyer(andrea);
        testVerkauft.setImagePath("/plant-images/mixBlumen.jpg");
        testVerkauft.setCareTip(careTip1);
        testVerkauft.setCategory(category5);
        plantService.savePlantDataLoader(testVerkauft);

        Plant testNoBeziehungen = new Plant();
        testNoBeziehungen.setName("Tulpe");
        testNoBeziehungen.setPrice(new BigDecimal("5.00"));
        testNoBeziehungen.setHeight(new BigDecimal("10.00"));
        testNoBeziehungen.setDescription("in rot");
        testNoBeziehungen.setSeller(admin);
        testNoBeziehungen.setImagePath("/plant-images/Tulpen.jpg");
        testNoBeziehungen.setCategory(category6);
        testNoBeziehungen.setCareTip(careTip3);
        testNoBeziehungen.setCreatedAt(anInstantPlant2);
        plantService.savePlantDataLoader(testNoBeziehungen);

        Plant orangenbaum = new Plant();
        orangenbaum.setName("Orangenbaum");
        orangenbaum.setPrice(new BigDecimal("234.00"));
        orangenbaum.setHeight(new BigDecimal("60.00"));
        orangenbaum.setDescription("hat leckere Orangen");
        orangenbaum.setSeller(admin);
        orangenbaum.setImagePath("/plant-images/orangenbaum.JPG");
        orangenbaum.setCategory(category1);
        orangenbaum.setCreatedAt(anInstantPlant3);
        plantService.savePlantDataLoader(orangenbaum);

        Plant monstera = new Plant();
        monstera.setName("Monstera");
        monstera.setPrice(new BigDecimal("35.00"));
        monstera.setHeight(new BigDecimal("35.00"));
        monstera.setDescription("hat sehr große Blätter");
        monstera.setSeller(admin);
        monstera.setImagePath("/plant-images/monstera.JPG");
        monstera.setCategory(category2);
        monstera.setPotIncluded(true);
        monstera.setCreatedAt(anInstantPlant4);
        plantService.savePlantDataLoader(monstera);

        Plant testData1 = new Plant();
        testData1.setName("Kaktus");
        testData1.setPrice(new BigDecimal("15.00"));
        testData1.setHeight(new BigDecimal("40.00"));
        testData1.setDescription("Ist sehr stachelig");
        testData1.setSeller(sena);
        testData1.setImagePath("/plant-images/testData1.JPG");
        testData1.setCategory(category3);
        testData1.setCareTip(careTip2);
        testData1.setPotIncluded(true);
        testData1.setCreatedAt(anInstantPlant5);
        plantService.savePlantDataLoader(testData1);

        Plant testData2 = new Plant();
        testData2.setName("Kaktus");
        testData2.setPrice(new BigDecimal("17.00"));
        testData2.setHeight(new BigDecimal("30.00"));
        testData2.setDescription("Er strahlt im einem besonderen Grün");
        testData2.setSeller(sena);
        testData2.setImagePath("/plant-images/testData2.JPG");
        testData2.setCategory(category3);
        testData2.setCareTip(careTip2);
        testData2.setCreatedAt(anInstantPlant6);
        plantService.savePlantDataLoader(testData2);

        Plant testData3 = new Plant();
        testData3.setName("Rose");
        testData3.setPrice(new BigDecimal("23.00"));
        testData3.setHeight(new BigDecimal("10.00"));
        testData3.setDescription("Sie ist rot wie die Liebe und richt sehr gut");
        testData3.setSeller(andrea);
        testData3.setImagePath("/plant-images/testData3.JPG");
        testData3.setCategory(category4);
        testData3.setCareTip(careTip1);
        testData3.setPotIncluded(true);
        testData3.setCreatedAt(anInstantPlant7);
        plantService.savePlantDataLoader(testData3);

        Plant testData4 = new Plant();
        testData4.setName("Zitronenbaum");
        testData4.setPrice(new BigDecimal("65.00"));
        testData4.setHeight(new BigDecimal("60.00"));
        testData4.setDescription("Hat immer sehr viele Früchte");
        testData4.setSeller(sena);
        testData4.setImagePath("/plant-images/testData4.JPG");
        testData4.setCategory(category1);
        testData4.setCreatedAt(anInstantPlant8);
        plantService.savePlantDataLoader(testData4);

        Plant testData5 = new Plant();
        testData5.setName("Elephantenohr");
        testData5.setPrice(new BigDecimal("45.00"));
        testData5.setHeight(new BigDecimal("33.00"));
        testData5.setDescription("Hat sehr sehr große Blätter");
        testData5.setSeller(sharon);
        testData5.setImagePath("/plant-images/testData5.JPG");
        testData5.setPotIncluded(true);
        testData5.setCategory(category2);
        testData5.setCreatedAt(anInstantPlant9);
        plantService.savePlantDataLoader(testData5);

        Plant testData6 = new Plant();
        testData6.setName("Rundkaktus");
        testData6.setPrice(new BigDecimal("15.00"));
        testData6.setHeight(new BigDecimal("22.00"));
        testData6.setDescription(" Ist nicht sehr groß ");
        testData6.setSeller(natti);
        testData6.setImagePath("/plant-images/testData6.JPG");
        testData6.setCategory(category4);
        testData6.setCareTip(careTip2);
        testData6.setCreatedAt(anInstantPlant10);
        plantService.savePlantDataLoader(testData6);

        Plant testData7 = new Plant();
        testData7.setName("Narzissen");
        testData7.setPrice(new BigDecimal("10.00"));
        testData7.setHeight(new BigDecimal("4.00"));
        testData7.setDescription("Strahlen wie die Sonne");
        testData7.setSeller(sena);
        testData7.setImagePath("/plant-images/testData7.JPG");
        testData7.setCategory(category6);
        testData7.setCreatedAt(anInstantPlant11);
        plantService.savePlantDataLoader(testData7);

        Plant testData8 = new Plant();
        testData8.setName("Grasstrauch");
        testData8.setPrice(new BigDecimal("13.00"));
        testData8.setHeight(new BigDecimal("30.00"));
        testData8.setDescription("Gibt einen guten Geruch");
        testData8.setSeller(natti);
        testData8.setImagePath("/plant-images/Graspflanze.jpeg");
        testData8.setCategory(category4);
        testData8.setCreatedAt(anInstantPlant12);
        plantService.savePlantDataLoader(testData8);

        Plant testData9 = new Plant();
        testData9.setName("Wasserrose");
        testData9.setPrice(new BigDecimal("13.00"));
        testData9.setHeight(new BigDecimal("30.00"));
        testData9.setDescription("Muss nicht nur immer Wasser gehalten werden");
        testData9.setSeller(sena);
        testData9.setImagePath("/plant-images/testData9.JPG");
        testData9.setCategory(category2);
        testData9.setPotIncluded(true);
        plantService.savePlantDataLoader(testData9);

        Plant testData10 = new Plant();
        testData10.setName("Flora");
        testData10.setPrice(new BigDecimal("5.00"));
        testData10.setHeight(new BigDecimal("6.00"));
        testData10.setDescription("Wir nur sehr klein und kommt ohne Wasser zurecht");
        testData10.setSeller(sena);
        testData10.setImagePath("/plant-images/testData10.JPG");
        testData10.setCategory(category4);
        plantService.savePlantDataLoader(testData10);

        Plant testData11 = new Plant();
        testData11.setName("Kaktussammlung");
        testData11.setPrice(new BigDecimal("11.00"));
        testData11.setHeight(new BigDecimal("32.00"));
        testData11.setDescription("Sammlung verschiedner Kaktus Arten");
        testData11.setSeller(sena);
        testData11.setImagePath("/plant-images/testData11.JPG");
        testData11.setPotIncluded(true);
        testData11.setCareTip(careTip2);
        testData11.setCategory(category3);
        plantService.savePlantDataLoader(testData11);

        Plant testData12 = new Plant();
        testData12.setName("Gummibaum");
        testData12.setPrice(new BigDecimal("12.00"));
        testData12.setHeight(new BigDecimal("24.00"));
        testData12.setDescription("Sehr schön");
        testData12.setSeller(sharon);
        testData12.setImagePath("/plant-images/testData12.JPG");
        testData12.setPotIncluded(true);
        testData12.setCategory(category2);
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