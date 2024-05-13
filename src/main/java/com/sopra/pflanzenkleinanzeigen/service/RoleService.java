package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.Rolle;
import com.sopra.pflanzenkleinanzeigen.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Rolle saveRole(Rolle role) {
        return roleRepository.save(role);
    }

    public List<Rolle> findAllRoles() {
        return roleRepository.findAll();
    }
}
