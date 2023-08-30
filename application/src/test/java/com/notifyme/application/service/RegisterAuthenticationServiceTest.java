package com.notifyme.application.service;

import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.User;
import com.notifyme.application.model.UserType;
import com.notifyme.application.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RegisterAuthenticationServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VerificationTokenRepository tokenRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private RegisterAuthenticationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegisterAuthenticationService(authenticationManager,
                userRepository, tokenRepository, customerRepository,
                passwordEncoder, jwtService, employeeRepository,
                adminRepository, eventPublisher);
    }


    @Test
    void canRegisterNewCustomer() {
        //given
        final String email = "c.e@email.com";
        final Long iid = 123L;
        final UserType customerType = UserType.CUSTOMER;
        when(userRepository.existsByEmailAddress(email)).thenReturn(false);

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                email, email, "firstName", "lastName", "dummyAddress",
                "noPas5Wo3d!10", "noPas5Wo3d!10", "+40-753-356-122",
                customerType.toString());
        String passwordHash = "¢5554ml;f;lsd";
        when(passwordEncoder.encode(userRegisterRequest.getPassword())).thenReturn(passwordHash);

        User newUser = new User();
        newUser.setEmailAddress(email);
        newUser.setIID(iid);
        when(userRepository.getUserByEmailAddress(email)).thenReturn(newUser);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("http://localhost:8080");
        request.setRequestURI("/api/auth/register");
        //when
        underTest.registerNewCustomer(userRegisterRequest, request);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getIID()).isNull();
        assertThat(capturedUser.getType()).isEqualTo(customerType);
        assertThat(capturedUser.getLastName()).isEqualTo("lastName");
        assertThat(capturedUser.getFirstName()).isEqualTo("firstName");
//        Assert.assertEquals(ResponseEntity.badRequest().body("Email used"), response);

    }

    @Test
    void willReturnWhenEmailIsTaken() {
        // given
        final String email = "c.e@email.com";
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail(email);
        when(userRepository.existsByEmailAddress(email)).thenReturn(true);
        // when
        ResponseEntity<?> response = underTest.registerNewCustomer(registerRequest, null);
        // then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Email used", response.getBody());
    }

    @ParameterizedTest
    @EnumSource(UserType.class)
    void validRoles(UserType type) {
        assertDoesNotThrow(() -> validateRole(type.toString()));

    }

    private void validateRole(String type) {
        if (type == null
                || !(type.toUpperCase(Locale.ROOT).equals("ADMIN")
                || type.toUpperCase(Locale.ROOT).equals("CUSTOMER")
                || type.toUpperCase(Locale.ROOT).equals("EMPLOYEE"))) {
            throw new RuntimeException("Invalid role");
        }
    }

    @Test
    void invalidRole() {
        assertThrows(RuntimeException.class, () -> validateRole("INVALID_ROLE"));
    }

    @Test
    void nullRole() {
        assertThrows(RuntimeException.class, () -> validateRole(null));
    }

    @Test
    void loginUser() {
    }
}