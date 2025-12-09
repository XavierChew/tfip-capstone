package com.gotbufffetleh.backend.processor;

import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.repositories.UserRepository;

import org.springframework.stereotype.Component;


@Component
public class LoginProcessor {
    private String username;
    private String password;
    private UserRepository userRepository;

    public LoginProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User login(String email, String password ) {
        return userRepository.findByEmail(email).map(user -> {
            if (user.getPassword().equals(password)){
                return user;
            }
            return null;
        }).orElse(null);


    }
}
