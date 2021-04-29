package com.example.crudwithvaadin.service;

import com.example.crudwithvaadin.model.UserDetails;
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

    public UserDetails saveUser(final UserDetails userDetails) {
        final String password = userDetails.getPassword();
        final String encodedPassword = bCryptPasswordEncoder.encode(password);
        userDetails.setPassword(encodedPassword);
        return userRepository.save(userDetails);
    }

    public String validateHandle(String handle) {
        return "";
    }

    public boolean checkIfUserNameAlreadyExist(final String userName)
    {
        UserDetails userDetails = userRepository.findByUserName(userName);
        if(userDetails == null)
        {
            return  false;
        }
        return true;
    }
}
