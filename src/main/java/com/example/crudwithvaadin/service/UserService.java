package com.example.crudwithvaadin.service;

import com.example.crudwithvaadin.model.User;
import com.example.crudwithvaadin.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public String validateHandle(String handle) {
        return "";
    }
}
