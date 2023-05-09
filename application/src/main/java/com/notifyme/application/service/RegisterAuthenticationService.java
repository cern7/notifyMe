package com.notifyme.application.service;

import com.notifyme.application.dto.AuthenticationRequest;
import com.notifyme.application.dto.AuthenticationResponse;
import com.notifyme.application.dto.UserDTO;
import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.*;

import com.notifyme.application.repository.AdminRepository;
import com.notifyme.application.repository.CustomerRepository;
import com.notifyme.application.repository.EmployeeRepository;
import com.notifyme.application.repository.UserRepository;
import com.notifyme.application.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Locale;

@Slf4j
@Service
public class RegisterAuthenticationService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;


    public RegisterAuthenticationService(AuthenticationManager authenticationManager,
                                         UserRepository userRepository,
                                         CustomerRepository customerRepository,
                                         PasswordEncoder passwordEncoder,
                                         JWTService jwtService,
                                         EmployeeRepository employeeRepository,
                                         AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
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
                userCustomer.setUser(user);
                customerRepository.save(userCustomer);
            }
            case "EMPLOYEE" -> {
                Employee userEmployee = new Employee(iid);
                userEmployee.setUser(user);
                employeeRepository.save(userEmployee);
            }
            case "ADMIN" -> {
                Admin userAdmin = new Admin(iid);
                userAdmin.setUser(user);
                adminRepository.save(userAdmin);
            }
            default -> {
                log.warn("No User Type provided");
            }
        }

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> loginUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .get(0);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new AuthenticationResponse(jwtCookie.getValue(),
                        new UserDTO(userDetails.getIID(),
                                userDetails.getFirstName(),
                                userDetails.getLastName(),
                                userDetails.getEmailAddress(),
                                userDetails.getPhoneNumber(),
                                userDetails.getType().toString())));
    }
}
