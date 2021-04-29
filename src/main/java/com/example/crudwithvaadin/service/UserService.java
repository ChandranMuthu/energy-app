package com.example.crudwithvaadin.service;

import com.example.crudwithvaadin.model.User;
import com.example.crudwithvaadin.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User saveUser(final User user) {
        final String password = user.getPassword();
        final String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public String validateHandle(String handle) {
        return "";
    }

    public boolean checkIfUserNameAlreadyExist(final String userName)
    {
        User user = userRepository.findByUserName(userName);
        if(user == null)
        {
            return  false;
        }
        return true;
    }
}
