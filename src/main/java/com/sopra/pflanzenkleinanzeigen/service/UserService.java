package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Plant;
import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
import com.sopra.pflanzenkleinanzeigen.repository.PlantRepository;
import com.sopra.pflanzenkleinanzeigen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

//TODO: "SaveUser" nochmal überprüfen, brauchen wir überhaupt try and catch sowie fehlermeldungen? Ich glaube es ist nicht nötigt.
// Außerdem diese Funktion wurde uns in die wiki so gegeben wie es war, daher denke ich auch das es nicht nötig ist zu ändern.

/**
 * This class encapsulates access to the UserRepository. It provides methods for managing User entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    //TODO: Exceptions testen bei der Registrierung von Benutzern
    /**
     * Saves a user to the repository.
     * If the username is already taken, an IllegalArgumentException is thrown.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public Benutzer saveUser(Benutzer user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("Benutzer darf nicht null sein");
            }

            Benutzer existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser == null) {
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Benutzername bereits vergeben");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Fehler beim Speichern des Benutzers: " + e.getMessage(), e);
            //TODO: Weiterleitung zu einer Fehlerseite oder Anzeige einer Fehlermeldung auf der aktuellen Seite
            throw e;
        } catch (Exception e) {
            logger.error("Unbekannter Fehler beim Speichern des Benutzers", e);
            //TODO: Weiterleitung zu einer Fehlerseite oder Anzeige einer Fehlermeldung auf der aktuellen Seite
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        }
    }


    public List<Benutzer> findAllUsers() {
        return userRepository.findAll();
    }

    public Benutzer getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Spring Security Authentication Methoden
    ///////////////////////////////////////////////////////////////////////////


    public Benutzer getCurrentUser() {
        return getUserByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());
    }

    public org.springframework.security.core.userdetails.User getCurrentUserDetails() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    /**
     * Overrides the method required for Spring Security login.
     *
     * @param username The username of the user.
     * @return The UserDetails object of the user.
     * @throws UsernameNotFoundException If the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Der Benutzer für den Benutzernamen konnte nicht gefunden werden " + username);
        }
        List<GrantedAuthority> grantedAuthorities = getUserAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, user.isEnabled(), grantedAuthorities);
    }

    /**
     * Gets the authorities of a user.
     *
     * @param roleSet The roles of the user.
     * @return A list of the user's authorities.
     */
    private List<GrantedAuthority> getUserAuthorities(Set<Rolle> roleSet) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Rolle role : roleSet) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }
        return grantedAuthorities;
    }

    /**
     * Finds and returns a list of active plants sold by a specific seller.
     *
     * @param sellerId The ID of the seller whose active plants are to be found.
     * @return A list of active plants sold by the seller. If the seller does not exist, an empty list is returned.
     */
    public List<Plant> findActivePlantsBySeller(Integer sellerId) {
        Benutzer seller = userRepository.findById(sellerId).orElse(null);
        if (seller != null) {
            return plantRepository.findAllActivePlantsBySeller(seller);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Finds and returns a list of plants sold by a specific seller and with a specific name.
     *
     * @param name The name of the plants to be found.
     * @param sellerId The ID of the seller whose plants are to be found.
     * @return A list of plants sold by the seller and with the specified name. If the seller does not exist, an empty list is returned.
     */
    public List<Plant> findPlantsByNameAndSeller(String name, Integer sellerId) {
        Benutzer seller = userRepository.findById(sellerId).orElse(null);
        if (seller != null) {
            return plantRepository.findByNameAndSeller(name, seller);
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * Finds and returns a list of plants purchased by a specific buyer and with a specific name.
     *
     * @param name The name of the plants to be found.
     * @param buyerId The ID of the buyer whose purchased plants are to be found.
     * @return A list of plants purchased by the buyer and with the specified name. If the buyer does not exist, an empty list is returned.
     */
    public List<Plant> findPurchasedPlantsByNameAndBuyer(String name, Integer buyerId) {
        Benutzer buyer = userRepository.findById(buyerId).orElse(null);
        if (buyer != null) {
            return plantRepository.findPurchasedPlantsByNameAndBuyer(name, buyer);
        } else {
            return new ArrayList<>();
        }
    }
}

