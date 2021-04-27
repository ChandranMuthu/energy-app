package com.example.crudwithvaadin.service;

import com.example.crudwithvaadin.model.User;
import com.example.crudwithvaadin.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
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
