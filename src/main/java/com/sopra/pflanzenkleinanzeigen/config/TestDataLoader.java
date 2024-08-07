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
        careTip1.setOtherTips("Die Pflanze ist eher für erfahrene Gärtner geeignet. Sie sollt nach jeder Blühzeit beschnitten werden. Zu beachten ist das sie Dornen hat.");
        careTip1.setRepotting("Ja, nach 4 Wochen");
        careTip1.setTemperature("20");
        careTip1.setPlanting("Kann direkt in die Erde");
        careTipService.saveCareTip(careTip1);

        CareTip careTip2 = new CareTip();
        careTip2.setCategory(category7);
        careTip2.setPlantName("Kaktus");
        careTip2.setLightingConditions("Mittel");
        careTip2.setFertilization("Wenig");
        careTip2.setIrrigation("Wenig");
        careTip2.setOtherTips("Diese Pflanze ist ideal für Anfänger geeignet, da sie auch bei gelegentlicher Vernachlässigung nicht sofort eingeht. Allerdings sollte man beachten, dass sie Dornen hat.");
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
        careTip4.setOtherTips("Ein Rückschnitt im Frühjahr fördert die Fruchtbildung. Im Sommer empfiehlt es sich, ein Netz über die Pflanze zu spannen, um die Früchte vor Vögeln zu schützen.");
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
        careTip15.setOtherTips("Karotten brauchen einen lockeren Boden, um gerade zu wachsen.");
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
        kaktus.setHeight(new BigDecimal("24.09"));
        kaktus.setDescription("Zu verkaufen ist ein wunderschöner, frischer Kaktus in einem schlichten Topf, verziert mit dekorativen Steinen. Diese pflegeleichte Pflanze verleiht jedem Raum eine besondere Ausstrahlung und passt perfekt in jede Einrichtung.");
        kaktus.setSeller(admin);
        kaktus.setImagePath("/plant-images/kaktus1.JPG");
        kaktus.setCategory(category7);
        kaktus.setCareTip(careTip2);
        kaktus.setPotIncluded(true);
        kaktus.setBuyer(andrea);
        kaktus.setSeed(true);
        kaktus.setLifespan("Mehrjährig");
        kaktus.setColor("Grün");
        kaktus.setAirPurifying(false);
        kaktus.setFloweringTime("Sommer");
        kaktus.setGrowthRate("Langsam");
        kaktus.setUsability("Dekorativ");
        kaktus.setLeafShape("Rund");
        kaktus.setStandort("Sonne");
        kaktus.setFruits(false);
        kaktus.setToxicForPets(false);
        plantService.savePlantDataLoader(kaktus);

        Plant rose = new Plant();
        rose.setName("Rose");
        rose.setPrice(new BigDecimal("15.99"));
        rose.setHeight(new BigDecimal("45.00"));
        rose.setDescription("Eine wunderschöne Rose, die jeden Garten ziert.");
        rose.setSeller(admin);
        rose.setImagePath("/plant-images/rose1.JPG");
        rose.setCategory(category2);
        rose.setCareTip(careTip1);
        rose.setPotIncluded(false);
        rose.setSeed(false);
        rose.setLifespan("Mehrjährig");
        rose.setColor("Rot");
        rose.setAirPurifying(false);
        rose.setFloweringTime("Sommer");
        rose.setGrowthRate("Mittel");
        rose.setUsability("Dekorativ");
        rose.setLeafShape("Oval");
        rose.setStandort("Sonne");
        rose.setFruits(false);
        rose.setToxicForPets(true);
        rose.setCreatedAt(anInstantPlant1);
        plantService.savePlantDataLoader(rose);

        Plant tulpe = new Plant();
        tulpe.setName("Tulpe");
        tulpe.setPrice(new BigDecimal("2.99"));
        tulpe.setHeight(new BigDecimal("30.00"));
        tulpe.setDescription("Bunte Tulpen für den Frühlingsgarten.");
        tulpe.setSeller(admin);
        tulpe.setImagePath("/plant-images/Tulpen.jpg");
        tulpe.setCategory(category2);
        tulpe.setCareTip(careTip11);
        tulpe.setPotIncluded(false);
        tulpe.setSeed(false);
        tulpe.setLifespan("Einjährig");
        tulpe.setColor("Mehrfarbig");
        tulpe.setAirPurifying(false);
        tulpe.setFloweringTime("Frühling");
        tulpe.setGrowthRate("Schnell");
        tulpe.setUsability("Dekorativ");
        tulpe.setLeafShape("Lanzettlich");
        tulpe.setStandort("Sonne");
        tulpe.setFruits(false);
        tulpe.setToxicForPets(false);
        plantService.savePlantDataLoader(tulpe);

        Plant strauss = new Plant();
        strauss.setName("Strauß");
        strauss.setPrice(new BigDecimal("15.00"));
        strauss.setHeight(new BigDecimal("30.00"));
        strauss.setDescription("Ein schöner Strauß mit einer Mischung aus verschiedenen Blumen, ideal als Geschenk oder zur Dekoration.");
        strauss.setSeller(admin);
        strauss.setImagePath("/plant-images/mixBlumen.jpg");
        strauss.setCategory(category2);
        strauss.setCareTip(careTip18);
        strauss.setPotIncluded(false);
        strauss.setBuyer(andrea);
        strauss.setSeed(false);
        strauss.setLifespan("Einjährig");
        strauss.setColor("Mehrfarbig");
        strauss.setAirPurifying(false);
        strauss.setFloweringTime("Sommer");
        strauss.setGrowthRate("Schnell");
        strauss.setUsability("Dekorativ");
        strauss.setLeafShape("Oval");
        strauss.setStandort("Sonne");
        strauss.setFruits(false);
        strauss.setToxicForPets(false);
        plantService.savePlantDataLoader(strauss);

        Plant orangenbaum = new Plant();
        orangenbaum.setName("Orangenbaum");
        orangenbaum.setPrice(new BigDecimal("55.00"));
        orangenbaum.setHeight(new BigDecimal("150.00"));
        orangenbaum.setDescription("Ein Orangenbaum, der süße Früchte trägt.");
        orangenbaum.setSeller(natti);
        orangenbaum.setImagePath("/plant-images/orangenbaum.JPG");
        orangenbaum.setCategory(category1);
        orangenbaum.setCareTip(careTip4);
        orangenbaum.setPotIncluded(true);
        orangenbaum.setSeed(false);
        orangenbaum.setLifespan("Mehrjährig");
        orangenbaum.setColor("Grün");
        orangenbaum.setAirPurifying(false);
        orangenbaum.setFloweringTime("Ganzjährig");
        orangenbaum.setGrowthRate("Langsam");
        orangenbaum.setUsability("Essbar");
        orangenbaum.setLeafShape("Oval");
        orangenbaum.setStandort("Sonne");
        orangenbaum.setFruits(true);
        orangenbaum.setFruit("Orange");
        orangenbaum.setToxicForPets(false);
        plantService.savePlantDataLoader(orangenbaum);

        Plant monstera = new Plant();
        monstera.setName("Monstera");
        monstera.setPrice(new BigDecimal("30.00"));
        monstera.setHeight(new BigDecimal("100.00"));
        monstera.setDescription("Eine Monstera, perfekt für Innenräume.");
        monstera.setSeller(sharon);
        monstera.setImagePath("/plant-images/monstera.JPG");
        monstera.setCategory(category7);
        monstera.setCareTip(careTip9);
        monstera.setPotIncluded(true);
        monstera.setSeed(false);
        monstera.setLifespan("Mehrjährig");
        monstera.setColor("Grün");
        monstera.setAirPurifying(true);
        monstera.setFloweringTime("Sommer");
        monstera.setGrowthRate("Mittel");
        monstera.setUsability("Dekorativ");
        monstera.setLeafShape("Herzförmig");
        monstera.setStandort("Halbschatten");
        monstera.setFruits(false);
        monstera.setToxicForPets(true);
        plantService.savePlantDataLoader(monstera);

        Plant kaktus2 = new Plant();
        kaktus2.setName("Kaktus");
        kaktus2.setPrice(new BigDecimal("8.99"));
        kaktus2.setHeight(new BigDecimal("20.00"));
        kaktus2.setDescription("Ein pflegeleichter Kaktus steht zum verkauf.");
        kaktus2.setSeller(sharon);
        kaktus2.setImagePath("/plant-images/testData1.JPG");
        kaktus2.setCategory(category7);
        kaktus2.setCareTip(careTip2);
        kaktus2.setPotIncluded(false);
        kaktus2.setSeed(true);
        kaktus2.setLifespan("Mehrjährig");
        kaktus2.setColor("Grün");
        kaktus2.setAirPurifying(false);
        kaktus2.setFloweringTime("Sommer");
        kaktus2.setGrowthRate("Langsam");
        kaktus2.setUsability("Dekorativ");
        kaktus2.setLeafShape("Rund");
        kaktus2.setStandort("Sonne");
        kaktus2.setFruits(false);
        kaktus2.setToxicForPets(false);
        plantService.savePlantDataLoader(kaktus2);

        Plant kaktus3 = new Plant();
        kaktus3.setName("Kaktus");
        kaktus3.setPrice(new BigDecimal("7.50"));
        kaktus3.setHeight(new BigDecimal("15.00"));
        kaktus3.setDescription("Ein kleiner Kaktus, ideal für Anfänger. Wer möchte ihn kaufen?");
        kaktus3.setSeller(sena);
        kaktus3.setImagePath("/plant-images/testData2.JPG");
        kaktus3.setCategory(category7);
        kaktus3.setCareTip(careTip2);
        kaktus3.setPotIncluded(false);
        kaktus3.setSeed(true);
        kaktus3.setLifespan("Mehrjährig");
        kaktus3.setColor("Grün");
        kaktus3.setAirPurifying(false);
        kaktus3.setFloweringTime("Sommer");
        kaktus3.setGrowthRate("Langsam");
        kaktus3.setUsability("Dekorativ");
        kaktus3.setLeafShape("Rund");
        kaktus3.setStandort("Sonne");
        kaktus3.setFruits(false);
        kaktus3.setToxicForPets(false);
        plantService.savePlantDataLoader(kaktus3);

        Plant rose2 = new Plant();
        rose2.setName("Rose");
        rose2.setPrice(new BigDecimal("18.99"));
        rose2.setHeight(new BigDecimal("50.00"));
        rose2.setDescription("Eine wunderschöne Rose für Ihren Garten, die besonders schön blüht");
        rose2.setSeller(admin);
        rose2.setImagePath("/plant-images/testData3.JPG");
        rose2.setCategory(category2);
        rose2.setCareTip(careTip1);
        rose2.setPotIncluded(false);
        rose2.setSeed(true);
        rose2.setLifespan("Mehrjährig");
        rose2.setColor("Pink");
        rose2.setAirPurifying(false);
        rose2.setFloweringTime("Sommer");
        rose2.setGrowthRate("Mittel");
        rose2.setUsability("Dekorativ");
        rose2.setLeafShape("Oval");
        rose2.setStandort("Sonne");
        rose2.setFruits(false);
        rose2.setToxicForPets(false);
        plantService.savePlantDataLoader(rose2);

        Plant zitronenbaum = new Plant();
        zitronenbaum.setName("Zitronenbaum");
        zitronenbaum.setPrice(new BigDecimal("60.00"));
        zitronenbaum.setHeight(new BigDecimal("140.00"));
        zitronenbaum.setDescription("Ich verkaufe ein Zitronenbaum, der frische Zitronen trägt.");
        zitronenbaum.setSeller(sena);
        zitronenbaum.setImagePath("/plant-images/testData4.JPG");
        zitronenbaum.setCategory(category1);
        zitronenbaum.setCareTip(careTip4);
        zitronenbaum.setPotIncluded(true);
        zitronenbaum.setSeed(false);
        zitronenbaum.setLifespan("Mehrjährig");
        zitronenbaum.setColor("Grün");
        zitronenbaum.setAirPurifying(false);
        zitronenbaum.setFloweringTime("Ganzjährig");
        zitronenbaum.setGrowthRate("Langsam");
        zitronenbaum.setUsability("Essbar");
        zitronenbaum.setLeafShape("Oval");
        zitronenbaum.setStandort("Sonne");
        zitronenbaum.setFruits(true);
        zitronenbaum.setFruit("Zitronen");
        zitronenbaum.setToxicForPets(false);
        plantService.savePlantDataLoader(zitronenbaum);

        Plant elefantenohr = new Plant();
        elefantenohr.setName("Elefantenohr");
        elefantenohr.setPrice(new BigDecimal("25.00"));
        elefantenohr.setHeight(new BigDecimal("50.00"));
        elefantenohr.setDescription("Ich verkaufe eine Elefantenohr Pflanze. Das Elefantenohr ist eine beeindruckende Pflanze mit großen, dekorativen Blättern. Sie eignet sich hervorragend als Zimmerpflanze und verleiht jedem Raum eine exotische Note.");
        elefantenohr.setSeller(natti);
        elefantenohr.setImagePath("/plant-images/testData5.JPG");
        elefantenohr.setCategory(category7);
        elefantenohr.setCareTip(careTip24);
        elefantenohr.setPotIncluded(true);
        elefantenohr.setSeed(false);
        elefantenohr.setLifespan("Mehrjährig");
        elefantenohr.setColor("Grün");
        elefantenohr.setAirPurifying(true);
        elefantenohr.setFloweringTime("Ganzjährig");
        elefantenohr.setGrowthRate("Mittel");
        elefantenohr.setUsability("Dekorativ");
        elefantenohr.setLeafShape("Herzförmig");
        elefantenohr.setStandort("Indirektes Sonnenlicht");
        elefantenohr.setFruits(false);
        elefantenohr.setToxicForPets(true);
        plantService.savePlantDataLoader(elefantenohr);

        Plant rundkaktus = new Plant();
        rundkaktus.setName("Rundkaktus");
        rundkaktus.setPrice(new BigDecimal("20.00"));
        rundkaktus.setHeight(new BigDecimal("15.00"));
        rundkaktus.setDescription("Ein kleiner, runder Kaktus, der durch seine einzigartige Form und seine pflegeleichte Natur besticht.");
        rundkaktus.setSeller(andrea);
        rundkaktus.setImagePath("/plant-images/testData6.JPG");
        rundkaktus.setCategory(category7);
        rundkaktus.setCareTip(careTip2);
        rundkaktus.setPotIncluded(true);
        rundkaktus.setSeed(true);
        rundkaktus.setLifespan("Mehrjährig");
        rundkaktus.setColor("Grün");
        rundkaktus.setAirPurifying(false);
        rundkaktus.setFloweringTime("Sommer");
        rundkaktus.setGrowthRate("Langsam");
        rundkaktus.setUsability("Dekorativ");
        rundkaktus.setLeafShape("Rund");
        rundkaktus.setStandort("Sonne");
        rundkaktus.setFruits(false);
        rundkaktus.setToxicForPets(false);
        plantService.savePlantDataLoader(rundkaktus);

        Plant narzisse = new Plant();
        narzisse.setName("Narzisse");
        narzisse.setPrice(new BigDecimal("8.00"));
        narzisse.setHeight(new BigDecimal("25.00"));
        narzisse.setDescription("Für den Frühling perfekt eine Narzisse mit leuchtend gelben Blüten, die jeden Garten verschönern.");
        narzisse.setSeller(andrea);
        narzisse.setImagePath("/plant-images/testData7.JPG");
        narzisse.setCategory(category2);
        narzisse.setCareTip(careTip11);
        narzisse.setPotIncluded(true);
        narzisse.setSeed(true);
        narzisse.setLifespan("Einjährig");
        narzisse.setColor("Gelb");
        narzisse.setAirPurifying(false);
        narzisse.setFloweringTime("Frühling");
        narzisse.setGrowthRate("Mittel");
        narzisse.setUsability("Dekorativ");
        narzisse.setLeafShape("Linear");
        narzisse.setStandort("Sonne");
        narzisse.setFruits(false);
        narzisse.setToxicForPets(true);
        plantService.savePlantDataLoader(narzisse);

        Plant grasstrauch = new Plant();
        grasstrauch.setName("Grasstrauch");
        grasstrauch.setPrice(new BigDecimal("10.00"));
        grasstrauch.setHeight(new BigDecimal("45.00"));
        grasstrauch.setDescription("Der Grasstrauch ist eine robuste Pflanze mit feinen, grasartigen Blättern, die perfekt für Gärten und Terrassen geeignet ist. Sie steht jetzt zum verkauf für deinen Garten.");
        grasstrauch.setSeller(sena);
        grasstrauch.setImagePath("/plant-images/Graspflanze.jpeg");
        grasstrauch.setCategory(category4);
        grasstrauch.setCareTip(careTip20);
        grasstrauch.setPotIncluded(true);
        grasstrauch.setSeed(true);
        grasstrauch.setLifespan("Mehrjährig");
        grasstrauch.setColor("Grün");
        grasstrauch.setAirPurifying(true);
        grasstrauch.setFloweringTime("Sommer");
        grasstrauch.setGrowthRate("Schnell");
        grasstrauch.setUsability("Dekorativ");
        grasstrauch.setLeafShape("Linear");
        grasstrauch.setStandort("Halbschatten");
        grasstrauch.setFruits(false);
        grasstrauch.setToxicForPets(false);
        plantService.savePlantDataLoader(grasstrauch);

        Plant wasserrose = new Plant();
        wasserrose.setName("Wasserrose");
        wasserrose.setPrice(new BigDecimal("25.00"));
        wasserrose.setHeight(new BigDecimal("15.00"));
        wasserrose.setDescription("Wer hat einen See und möchte ihn schmücken? Ich verkaufe eine Wasserrose, die wunderschön blüht.");
        wasserrose.setSeller(natti);
        wasserrose.setImagePath("/plant-images/testData9.JPG");
        wasserrose.setCategory(category2);
        wasserrose.setCareTip(careTip20);
        wasserrose.setPotIncluded(false);
        wasserrose.setSeed(true);
        wasserrose.setLifespan("Mehrjährig");
        wasserrose.setColor("Mehrfarbig");
        wasserrose.setAirPurifying(false);
        wasserrose.setFloweringTime("Sommer");
        wasserrose.setGrowthRate("Langsam");
        wasserrose.setUsability("Dekorativ");
        wasserrose.setLeafShape("Rund");
        wasserrose.setStandort("Sonne");
        wasserrose.setFruits(false);
        wasserrose.setToxicForPets(false);
        plantService.savePlantDataLoader(wasserrose);

        Plant flora = new Plant();
        flora.setName("Flora");
        flora.setPrice(new BigDecimal("35.00"));
        flora.setHeight(new BigDecimal("75.00"));
        flora.setDescription("Flora ist eine prächtige Zierpflanze, die durch ihre auffälligen Blüten und ihr dichtes Laub besticht. Sie eignet sich hervorragend für große Räume und Gartenanlagen.");
        flora.setSeller(sharon);
        flora.setImagePath("/plant-images/testData10.JPG");
        flora.setCategory(category7);
        flora.setCareTip(careTip16);
        flora.setPotIncluded(true);
        flora.setSeed(false);
        flora.setLifespan("Mehrjährig");
        flora.setColor("Lila");
        flora.setAirPurifying(true);
        flora.setFloweringTime("Frühling");
        flora.setGrowthRate("Mittel");
        flora.setUsability("Dekorativ");
        flora.setLeafShape("Lanzettlich");
        flora.setStandort("Indirektes Sonnenlicht");
        flora.setFruits(false);
        flora.setToxicForPets(true);
        plantService.savePlantDataLoader(flora);

        Plant kaktussammlung = new Plant();
        kaktussammlung.setName("Kaktussammlung");
        kaktussammlung.setPrice(new BigDecimal("20.00"));
        kaktussammlung.setHeight(new BigDecimal("30.00"));
        kaktussammlung.setDescription("Eine vielfältige Sammlung von verschiedenen Kakteen, die jedem Raum ein exotisches Flair verleihen und sehr pflegeleicht sind.");
        kaktussammlung.setSeller(andrea);
        kaktussammlung.setImagePath("/plant-images/testData11.JPG");
        kaktussammlung.setCategory(category7);
        kaktussammlung.setCareTip(careTip2);
        kaktussammlung.setPotIncluded(true);
        kaktussammlung.setSeed(true);
        kaktussammlung.setLifespan("Mehrjährig");
        kaktussammlung.setColor("Grün");
        kaktussammlung.setAirPurifying(false);
        kaktussammlung.setFloweringTime("Sommer");
        kaktussammlung.setGrowthRate("Langsam");
        kaktussammlung.setUsability("Dekorativ");
        kaktussammlung.setLeafShape("Rund");
        kaktussammlung.setStandort("Sonne");
        kaktussammlung.setFruits(false);
        kaktussammlung.setToxicForPets(false);
        plantService.savePlantDataLoader(kaktussammlung);

        Plant gummibaum = new Plant();
        gummibaum.setName("Gummibaum");
        gummibaum.setPrice(new BigDecimal("30.00"));
        gummibaum.setHeight(new BigDecimal("100.00"));
        gummibaum.setDescription("Verkaufe einen Gummibaum. Der ist eine beliebte Zimmerpflanze mit großen, glänzenden Blättern, die wenig Pflege benötigt und für ein angenehmes Raumklima sorgt.");
        gummibaum.setSeller(natti);
        gummibaum.setImagePath("/plant-images/testData12.JPG");
        gummibaum.setCategory(category1);
        gummibaum.setCareTip(careTip24);
        gummibaum.setPotIncluded(true);
        gummibaum.setSeed(false);
        gummibaum.setLifespan("Mehrjährig");
        gummibaum.setColor("Grün");
        gummibaum.setAirPurifying(true);
        gummibaum.setFloweringTime("Ganzjährig");
        gummibaum.setGrowthRate("Langsam");
        gummibaum.setUsability("Dekorativ");
        gummibaum.setLeafShape("Oval");
        gummibaum.setStandort("Indirektes Sonnenlicht");
        gummibaum.setFruits(false);
        gummibaum.setToxicForPets(false);
        plantService.savePlantDataLoader(gummibaum);

        Chat chat1 = new Chat();
        chat1.setPlant(rundkaktus);
        chat1.setPossibleBuyer(sena);
        chatService.saveChat(chat1);

        Chat chat2 = new Chat();
        chat2.setPlant(narzisse);
        chat2.setPossibleBuyer(sharon);
        chatService.saveChat(chat2);

        Chat chat3 = new Chat();
        chat3.setPlant(kaktussammlung);
        chat3.setPossibleBuyer(sharon);
        chatService.saveChat(chat3);

        Instant anInstant1 = Instant.ofEpochSecond(1719998236);
        Instant anInstant2 = Instant.ofEpochSecond(1720001776);
        Instant anInstant3 = Instant.ofEpochSecond(1720001896);
        Instant anInstant4 = Instant.ofEpochSecond(1720009096);
        Instant anInstant5 = Instant.ofEpochSecond(1720183036);
        Instant anInstant6 = Instant.ofEpochSecond(1720185556);
        Instant anInstant7 = Instant.ofEpochSecond(1720187356);

        Message message1 = new Message();
        message1.setChat(chat1);
        message1.setSender(sena);
        message1.setMessageContent("Hallo, ich würde sehr sehr gerne dein Kaktus kaufen");
        message1.setSentAt(anInstant1);
        messageService.saveMessage(message1);

        Message message2 = new Message();
        message2.setChat(chat1);
        message2.setSender(andrea);
        message2.setMessageContent("Zu beachten ist, dass es ein Rundkaktus ist. der wächst anders als normale Kakteen");
        message2.setSentAt(anInstant2);
        messageService.saveMessage(message2);

        Message message3 = new Message();
        message3.setChat(chat1);
        message3.setSender(sena);
        message3.setMessageContent("Ok, den würde ich dann gerne kaufen!");
        message3.setSentAt(anInstant3);
        messageService.saveMessage(message3);


        Message message4 = new Message();
        message4.setChat(chat1);
        message4.setSender(andrea);
        message4.setMessageContent("Alles klar, du kann sie heute Abend, gegen 19 Uhr, abholen und bringst einfach das Geld in bar mit.");
        message4.setSentAt(anInstant4);
        messageService.saveMessage(message4);


        Message message5 = new Message();
        message5.setChat(chat2);
        message5.setSender(natti);
        message5.setMessageContent("Hallo :)");
        message5.setSentAt(anInstant5);
        messageService.saveMessage(message5);

        Message message6 = new Message();
        message6.setChat(chat2);
        message6.setSender(natti);
        message6.setMessageContent("Ich habe deine Pflanzenanzeige gesehen und suche genau diese Pflanze. Ich würde sie also gerne kaufen");
        message6.setSentAt(anInstant5);
        messageService.saveMessage(message6);

        Message message7 = new Message();
        message7.setChat(chat2);
        message7.setSender(andrea);
        message7.setMessageContent("Das freut mich, möchtest du lieber bar oder per paypal zahlen?");
        message7.setSentAt(anInstant6);
        messageService.saveMessage(message7);

        Message message8 = new Message();
        message8.setChat(chat2);
        message8.setSender(natti);
        message8.setMessageContent("Ich würde es gerne bar zahlen wenn ich es abhole. Passt es gleich heute bei dir?");
        message8.setSentAt(anInstant7);
        messageService.saveMessage(message8);

        Message message9 = new Message();
        message9.setChat(chat2);
        message9.setSender(andrea);
        message9.setMessageContent("Heute passt, jedoch erst ab 17 Uhr. Klingel einfach bei Chazarreta. :)");
        message9.setSentAt(anInstant7);
        messageService.saveMessage(message9);

        Message message10 = new Message();
        message10.setChat(chat3);
        message10.setSender(sharon);
        message10.setMessageContent("Hallo Andrea, aus welchen Kakteen besteht denn die Sammlung?");
        message10.setSentAt(anInstant6);
        messageService.saveMessage(message10);

        Instant anInstant8 = Instant.ofEpochSecond(1720190000);
        Instant anInstant9 = Instant.ofEpochSecond(1720190600);
        Instant anInstant10 = Instant.ofEpochSecond(1720191200);
        Instant anInstant11 = Instant.ofEpochSecond(1720191800);

        Message message11 = new Message();
        message11.setChat(chat2);
        message11.setSender(sharon);
        message11.setMessageContent("Hallo Andrea, ich bin sehr an deiner Narzisse interessiert. Ist sie noch zu haben?");
        message11.setSentAt(anInstant8);
        messageService.saveMessage(message11);

        Message message12 = new Message();
        message12.setChat(chat2);
        message12.setSender(andrea);
        message12.setMessageContent("Hallo Sharon, ja, die Narzisse ist noch verfügbar. Sie ist in einem sehr guten Zustand.");
        message12.setSentAt(anInstant9);
        messageService.saveMessage(message12);

        Message message13 = new Message();
        message13.setChat(chat2);
        message13.setSender(sharon);
        message13.setMessageContent("Super! Ich habe gelesen, dass sie giftig für Haustiere sein kann. Ich habe eine Katze, wäre das ein Problem?");
        message13.setSentAt(anInstant10);
        messageService.saveMessage(message13);

        Message message14 = new Message();
        message14.setChat(chat2);
        message14.setSender(andrea);
        message14.setMessageContent("Ja, das stimmt. Narzissen können für Haustiere giftig sein. Vielleicht wäre eine andere Pflanze besser geeignet?");
        message14.setSentAt(anInstant11);
        messageService.saveMessage(message14);

        Message message15 = new Message();
        message15.setChat(chat2);
        message15.setSender(sharon);
        message15.setMessageContent("Danke für den Hinweis. Hast du andere Pflanzen, die für Haustiere sicher sind?");
        message15.setSentAt(anInstant11);
        messageService.saveMessage(message15);

        Message message16 = new Message();
        message16.setChat(chat2);
        message16.setSender(andrea);
        message16.setMessageContent("Ja, ich habe einige andere Pflanzen, die für Haustiere sicher sind. Zum Beispiel habe ich eine schöne Sammlung von Kakteen.");
        message16.setSentAt(anInstant11);
        messageService.saveMessage(message16);

        Message message17 = new Message();
        message17.setChat(chat2);
        message17.setSender(sharon);
        message17.setMessageContent("Das klingt interessant. Kannst du mir mehr über die Kaktussammlung erzählen?");
        message17.setSentAt(anInstant11);
        messageService.saveMessage(message17);

        Message message18 = new Message();
        message18.setChat(chat2);
        message18.setSender(andrea);
        message18.setMessageContent("Die Kaktussammlung besteht aus verschiedenen Arten von Kakteen, alle sind pflegeleicht und ungiftig für Haustiere.");
        message18.setSentAt(anInstant11);
        messageService.saveMessage(message18);

        Message message19 = new Message();
        message19.setChat(chat2);
        message19.setSender(sharon);
        message19.setMessageContent("Das ist toll, danke! Wann kann ich es abholen?");
        message19.setSentAt(anInstant11);
        messageService.saveMessage(message19);

        Message message20 = new Message();
        message20.setChat(chat2);
        message20.setSender(andrea);
        message20.setMessageContent("Würde es am Wochenende passen?");
        message20.setSentAt(anInstant11);
        messageService.saveMessage(message20);

    }

}