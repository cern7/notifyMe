package com.notifyme.application.controller;

import com.notifyme.application.dto.AuthenticationRequest;
import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.service.RegisterAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class RegisterAuthenticationController {

    private final RegisterAuthenticationService registerAuthenticationService;

    public RegisterAuthenticationController(RegisterAuthenticationService registerAuthenticationService) {
        this.registerAuthenticationService = registerAuthenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerRequest) {
        return registerAuthenticationService.registerNewCustomer(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return registerAuthenticationService.loginUser(authenticationRequest);
    }
}
