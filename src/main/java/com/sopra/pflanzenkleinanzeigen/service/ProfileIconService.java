package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.ProfileIcon;
import com.sopra.pflanzenkleinanzeigen.repository.ProfileIconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class encapsulates access to the ProfileIconRepository. It provides methods for managing ProfileIcon entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class ProfileIconService {

    @Autowired
    private ProfileIconRepository profileIconRepository;

    public ProfileIcon saveProfileIcon(ProfileIcon profileIcon) {
        return profileIconRepository.save(profileIcon);
    }

    public List<ProfileIcon> findAllProfileIcons() {
        return profileIconRepository.findAll();
    }
}