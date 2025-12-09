package com.gotbufffetleh.backend.controller;


import com.gotbufffetleh.backend.dto.LoginRequest;
import com.gotbufffetleh.backend.processor.LoginProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
private final LoginProcessor loginProcessor;
public LoginController(LoginProcessor loginProcessor) { this.loginProcessor = loginProcessor; }



@PostMapping("/login")
    public String login(LoginRequest request) {


}
}
