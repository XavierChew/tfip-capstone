package com.gotbufffetleh.backend.processor;

import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.repositories.UserRepository;


import org.springframework.stereotype.Service;


@Service
public class LoginProcessor {
    private final UserRepository userRepository;

    //Autowire the UserRepo
    public LoginProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    // rmb to throw exception
    public User login(String email, String password ) {
        return userRepository.findByEmail(email).map(user -> {
            if (user.getPassword().equals(password)){
                return user;
            }
            return null;
        }).orElse(null);


    }
}
