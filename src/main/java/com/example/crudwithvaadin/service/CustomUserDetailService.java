package com.example.crudwithvaadin.service;

import com.example.crudwithvaadin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final com.example.crudwithvaadin.model.User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(user.getUserName()).password(user.getPassword()).authorities(user.getRole().toString()).build();
    }
}