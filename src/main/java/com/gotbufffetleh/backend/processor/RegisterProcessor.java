package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.dto.RegisterRequest;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterProcessor {
    private final UserRepository userRepository;

    //autowire
    public RegisterProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            System.out.println("Email already in use");
            return Optional.empty();
        }

        //invalid email address pattern
        if(!request.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            System.out.println("Invalid email pattern");
            return Optional.empty();
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setDisplayName(request.getDisplayName());

        User savedUser = userRepository.save(newUser);
        return Optional.of(savedUser);
    }
}
