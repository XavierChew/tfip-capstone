package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.dto.RegisterRequest;
import com.gotbufffetleh.backend.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterProcessor {
    private final UserRepository userRepository;

    //autowire
    public RegisterProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return Optional.empty();
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(request.getPassword());
        newUser.setDisplayName(request.getDisplayName());

        User savedUser = userRepository.save(newUser);
        return Optional.of(savedUser);
    }
}
