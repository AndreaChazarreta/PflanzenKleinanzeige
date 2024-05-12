package com.sopra.pflanzenkleinanzeigen.service;

import com.sopra.pflanzenkleinanzeigen.entity.User;
import com.sopra.pflanzenkleinanzeigen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
