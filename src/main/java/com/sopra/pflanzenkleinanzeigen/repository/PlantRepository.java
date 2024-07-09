package com.sopra.pflanzenkleinanzeigen.repository;

import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface provides methods to interact with the Plant entity in the database.
 * It extends JpaRepository which provides basic CRUD operations (Create, Read, Update, Delete) for the Plant entity.
 */
public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query("SELECT p FROM Plant p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Plant> findByKeywordName(@Param("name") String name);

    @Query("SELECT p FROM Plant p WHERE p.adIsActive = true")
    List<Plant> findAllActivePlants();

    @Query("SELECT p FROM Plant p WHERE p.seller = :seller AND p.adIsActive = true")
    List<Plant> findAllActivePlantsBySeller(@Param("seller") Benutzer seller);

    @Query("SELECT p FROM Plant p WHERE p.seller = :seller AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.adIsActive = true")
    List<Plant> findByNameAndSeller(@Param("name") String name, @Param("seller") Benutzer seller);

    @Query("SELECT p FROM Plant p WHERE p.buyer = :buyer AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Plant> findPurchasedPlantsByNameAndBuyer(@Param("name") String name, @Param("buyer") Benutzer buyer);

    @Query("SELECT p FROM Plant p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minHeight IS NULL OR p.height >= :minHeight) " +
            "AND (:maxHeight IS NULL OR p.height <= :maxHeight) " +
            "AND (:potIncluded IS NULL OR p.potIncluded = :potIncluded) " +
            "AND (:categories IS NULL OR p.category.name IN :categories) " +
            "AND (:excludeCurrentUser IS NULL OR p.seller <> :currentUser) " +
            "AND (:fruits IS NULL OR p.fruits = :fruits) " +
            "AND (:airPurifying IS NULL OR p.airPurifying = :airPurifying) " +
            "AND (:toxicForPets IS NULL OR p.toxicForPets = :toxicForPets) " +
            "AND p.adIsActive = true " +
            "ORDER BY CASE WHEN :sortPrice = 'asc' THEN p.price END ASC, " +
            "CASE WHEN :sortPrice = 'desc' THEN p.price END DESC")
    List<Plant> findByFilters(@Param("name") String name, @Param("minPrice") BigDecimal minPrice,
                              @Param("maxPrice") BigDecimal maxPrice, @Param("minHeight") BigDecimal minHeight,
                              @Param("maxHeight") BigDecimal maxHeight, @Param("potIncluded") Boolean potIncluded,
                              @Param("categories") List<String> categories,  @Param("excludeCurrentUser") Boolean excludeCurrentUser,
                              @Param("currentUser") Benutzer currentUser,  @Param("fruits") Boolean fruits,
                              @Param("airPurifying") Boolean airPurifying, @Param("toxicForPets") Boolean toxicForPets,
                              @Param("sortPrice") String sortPrice);


    @Query("SELECT p FROM Plant p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minHeight IS NULL OR p.height >= :minHeight) " +
            "AND (:maxHeight IS NULL OR p.height <= :maxHeight) " +
            "AND (:potIncluded IS NULL OR p.potIncluded = :potIncluded) " +
            "AND (:excludeCurrentUser IS NULL OR p.seller <> :currentUser) " +
            "AND (:fruits IS NULL OR p.fruits = :fruits) " +
            "AND (:airPurifying IS NULL OR p.airPurifying = :airPurifying) " +
            "AND (:toxicForPets IS NULL OR p.toxicForPets = :toxicForPets) " +
            "AND p.adIsActive = true " +
            "ORDER BY CASE WHEN :sortPrice = 'asc' THEN p.price END ASC, " +
            "CASE WHEN :sortPrice = 'desc' THEN p.price END DESC")
    List<Plant> findByFiltersWithoutCategory(@Param("name") String name, @Param("minPrice") BigDecimal minPrice,
                              @Param("maxPrice") BigDecimal maxPrice, @Param("minHeight") BigDecimal minHeight,
                              @Param("maxHeight") BigDecimal maxHeight, @Param("potIncluded") Boolean potIncluded,
                              @Param("excludeCurrentUser") Boolean excludeCurrentUser, @Param("currentUser") Benutzer currentUser,
                              @Param("fruits") Boolean fruits, @Param("airPurifying") Boolean airPurifying,
                              @Param("toxicForPets") Boolean toxicForPets, @Param("sortPrice") String sortPrice);

    @Query("SELECT MAX(p.price) FROM Plant p WHERE p.adIsActive = true")
    BigDecimal findMaxPrice();

    @Query("SELECT p FROM Plant p WHERE LOWER(p.name) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Plant> findByPrefix(@Param("prefix") String prefix);

    @Query("SELECT p FROM Plant p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(p.name) NOT LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Plant> findByNameContaining(@Param("name") String name, @Param("prefix") String prefix);
}

