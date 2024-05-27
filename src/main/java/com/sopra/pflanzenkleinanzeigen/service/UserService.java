package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import com.sopra.pflanzenkleinanzeigen.entity.Benutzer;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a user service in the system.
 * It contains methods to save a user, find all users, get a user by username, get the current user, get the current user details, load user by username, and get user authorities.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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


    /**
     * Finds all users in the repository.
     *
     * @return A list of all users.
     */
    public List<Benutzer> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by username.
     *
     * @param username The username of the user.
     * @return The user with the given username.
     */
    public Benutzer getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Spring Security Authentication Methoden
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Gets the currently logged in user.
     *
     * @return The currently logged in user.
     */
    public Benutzer getCurrentUser() {
        return getUserByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());
    }

    /**
     * Gets the UserDetails object of the currently logged in user.
     * This may be needed to perform role authentication checks.
     *
     * @return The UserDetails object of the currently logged in user.
     */
    public org.springframework.security.core.userdetails.User getCurrentUserDetails() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    /**
     * Loads a user by username.
     * This method is needed for Spring Security's login process.
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
}

