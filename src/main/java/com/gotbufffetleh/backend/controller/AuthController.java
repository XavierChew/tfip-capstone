package com.gotbufffetleh.backend.controller;


import com.gotbufffetleh.backend.dbTables.User;
import com.gotbufffetleh.backend.dto.LoginRequest;
import com.gotbufffetleh.backend.dto.RegisterRequest;
import com.gotbufffetleh.backend.processor.LoginProcessor;
import com.gotbufffetleh.backend.processor.RegisterProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
private final LoginProcessor loginProcessor;
private final RegisterProcessor registerProcessor;
public AuthController(LoginProcessor loginProcessor, RegisterProcessor registerProcessor) {
    this.loginProcessor = loginProcessor;
    this.registerProcessor = registerProcessor;
}

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    User loggedInUser = loginProcessor.login(request.getEmail(), request.getPassword());

    if (loggedInUser != null) {
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
    else {
        return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED);
    }
}

@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

    Optional<User> registeredUser = registerProcessor.register(request);

    if (registeredUser.isPresent()) {
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }
    else {
        return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED);
    }
}


}
