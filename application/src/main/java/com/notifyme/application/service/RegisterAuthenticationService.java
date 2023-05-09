package com.notifyme.application.service;

import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.Customer;
import com.notifyme.application.model.User;
import com.notifyme.application.model.UserStatus;

import com.notifyme.application.model.UserType;
import com.notifyme.application.repository.CustomerRepository;
import com.notifyme.application.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Locale;

@Slf4j
@Service
public class RegisterAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;


    public RegisterAuthenticationService(AuthenticationManager authenticationManager,
                                         UserRepository userRepository,
                                         CustomerRepository customerRepository,
                                         PasswordEncoder passwordEncoder,
                                         JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public ResponseEntity<?> registerNewCustomer(UserRegisterRequest registerRequest) {
        if (userRepository.existsByEmailAddress(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email used");
        }

        String type = registerRequest.getType();

        if (type == null
                || !(type.toUpperCase(Locale.ROOT).equals("ADMIN")
                || type.toUpperCase(Locale.ROOT).equals("CUSTOMER")
                || type.toUpperCase(Locale.ROOT).equals("EMPLOYEE"))) {
            throw new RuntimeException("Invalid role");
        }
        // TODO: add more sanity check (email, password etc...)
        User user = new User(null, registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPhoneNumber(),
                UserStatus.PENDING,
                UserType.valueOf(registerRequest.getType()),
                registerRequest.getGeographicAddress(),
                new Date().toString(),
                null,
                passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        // is it saved, need to be updated refresh
        Long iid = userRepository.getUserByEmailAddress(registerRequest.getEmail()).getIID();

        switch (registerRequest.getType().toUpperCase(Locale.ROOT)) {
            case "CUSTOMER" -> {
                Customer userCustomer = new Customer(iid);
                customerRepository.save(userCustomer);
            }
            case "EMPLOYEE" -> {
                // save to employeee
            }
            default -> {
                // save to admin
            }
        }

        return ResponseEntity.ok("User registered successfully");
    }
}
