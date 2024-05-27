package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.ProfileIcon;
import com.sopra.pflanzenkleinanzeigen.repository.ProfileIconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents a profile icon service in the system.
 * It contains methods to save a profile icon and to find all profile icons.
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