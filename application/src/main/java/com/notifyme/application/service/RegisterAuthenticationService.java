package com.notifyme.application.service;

import com.notifyme.application.dto.*;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.model.*;
import com.notifyme.application.repository.*;
import com.notifyme.application.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class RegisterAuthenticationService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ApplicationEventPublisher eventPublisher;


    public RegisterAuthenticationService(AuthenticationManager authenticationManager,
                                         UserRepository userRepository,
                                         VerificationTokenRepository tokenRepository,
                                         CustomerRepository customerRepository,
                                         PasswordEncoder passwordEncoder,
                                         JWTService jwtService,
                                         EmployeeRepository employeeRepository,
                                         AdminRepository adminRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ResponseEntity<?> registerNewCustomer(UserRegisterRequest registerRequest,
                                                 HttpServletRequest request) {
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
        if (!registerRequest.getEmail().equals(registerRequest.getConfirmEmail())) {
            throw new IllegalArgumentException("Email doesn't match");
        }
        User user = new User(null, registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPhoneNumber(),
                UserStatus.PENDING,
                UserType.valueOf(type),
                registerRequest.getGeographicAddress(),
                new Date().toString(),
                null,
                passwordEncoder.encode(registerRequest.getPassword()));

        // 1. save user in usertbl
        userRepository.save(user);

        Long iid = userRepository.getUserByEmailAddress(registerRequest.getEmail()).getIID();

        // 2. save user in user type specific table (customer, employee, admin)
        saveUserBasedOnType(iid, user, type.toUpperCase(Locale.ROOT));

        publishEvent(user, request);

        return ResponseEntity.ok("User registered successfully");
    }

    private boolean publishEvent(User user, HttpServletRequest request) {
        try {
            String appUrl = request.getContextPath();// appUrl is empty???
            eventPublisher.publishEvent(new GenericEvent<>(
                    new RegisterEventDTO(user, request.getLocale(), appUrl)));
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Send email not published");
        }
    }

    @Transactional
    public void saveUserBasedOnType(Long userId, User user, String userType) {
        switch (userType) {
            case "CUSTOMER" -> {
                Customer userCustomer = new Customer(userId);
                userCustomer.setUser(user);
                customerRepository.save(userCustomer);
            }
            case "EMPLOYEE" -> {
                Employee userEmployee = new Employee(userId);
                userEmployee.setUser(user);
                employeeRepository.save(userEmployee);
            }
            case "ADMIN" -> {
                Admin userAdmin = new Admin(userId);
                userAdmin.setUser(user);
                adminRepository.save(userAdmin);
            }
            default -> {
                log.warn("No User Type provided");
            }
        }
    }


    @Transactional
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

        Date loginTime = new Date();
        userRepository.updateLoginTime(userDetails.getIID(), loginTime.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new AuthenticationResponse(jwtCookie.getValue(),
                        new UserDTO(userDetails.getIID(),
                                userDetails.getFirstName(),
                                userDetails.getLastName(),
                                userDetails.getEmailAddress(),
                                userDetails.getPhoneNumber(),
                                userDetails.getType())));
    }


    @Transactional
    public synchronized void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(final String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    public String getVerificationToken(final User user) {
        if (user == null) {
            return "User doesn't exist";
        }
        return tokenRepository.findByUserId(user.getIID()).orElse(null);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TokenStatus.INVALIDTOKEN.value();
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            // TODO:
            // delete the token from the repo if expired
            return TokenStatus.EXPIREDTOKEN.value();
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return TokenStatus.VALIDTOKEN.value();
    }

    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

}
