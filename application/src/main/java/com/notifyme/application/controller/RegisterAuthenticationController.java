package com.notifyme.application.controller;

import com.notifyme.application.dto.AuthenticationRequest;
import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.TokenStatus;
import com.notifyme.application.service.RegisterAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
public class RegisterAuthenticationController {

    private final RegisterAuthenticationService registerAuthenticationService;


    public RegisterAuthenticationController(
            RegisterAuthenticationService registerAuthenticationService) {
        this.registerAuthenticationService = registerAuthenticationService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest registerRequest,
                                      HttpServletRequest request) {
        return registerAuthenticationService.registerNewCustomer(registerRequest, request);
    }

    @PostMapping("/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(WebRequest request,
                                                 @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        final String result = registerAuthenticationService.validateVerificationToken(token);
        if (result.equals(TokenStatus.VALIDTOKEN.value())) {
            return ResponseEntity.ok("Email confirmed");
        } else if (result.equals(TokenStatus.INVALIDTOKEN.value())
                || result.equals(TokenStatus.EXPIREDTOKEN.value())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INVALID TOKEN");
        }

        return ResponseEntity.badRequest().body("SOMETHING WENT WRONG");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return registerAuthenticationService.loginUser(authenticationRequest);
    }


}
