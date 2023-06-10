package com.notifyme.application.controller;

import com.notifyme.application.dto.AuthenticationRequest;
import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.User;
import com.notifyme.application.model.VerificationToken;
import com.notifyme.application.repository.VerificationTokenRepository;
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
                                                 Model model,
                                                 @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        final String result = registerAuthenticationService.validateVerificationToken(token);
        if (result.equals("TOKEN VALID")) {
            return ResponseEntity.ok("Email confirmed"); // REDIRECT TO LOGIN
//            return "redirect:/login.html?lang=" + locale.getLanguage();
        } else if (result.equals("TOKEN INVALID")
                || result.equals("TOKEN EXPIRED")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INVALID TOKEN");
            // REDIRECT TO WHAT??????
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        return ResponseEntity.badRequest().body("SOMETHING WENT WRONG");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return registerAuthenticationService.loginUser(authenticationRequest);
    }


}
