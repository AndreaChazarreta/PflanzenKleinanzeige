package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import com.sopra.pflanzenkleinanzeigen.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class encapsulates access to the RoleRepository. It provides methods for managing Role entities
 * without exposing direct access to the repository from outside the service layer.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Rolle saveRole(Rolle role) {
        return roleRepository.save(role);
    }

}
