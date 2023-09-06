package com.notifyme.application.service;

import com.notifyme.application.dto.UserRegisterRequest;
import com.notifyme.application.model.TokenStatus;
import com.notifyme.application.model.User;
import com.notifyme.application.model.UserType;
import com.notifyme.application.model.VerificationToken;
import com.notifyme.application.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterAuthenticationServiceTest {

    private static final int EXPIRATION = 60 * 24;
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
    @Mock
    private VerificationToken token;

    @BeforeEach
    void setUp() {
        underTest = new RegisterAuthenticationService(authenticationManager,
                userRepository, tokenRepository, customerRepository,
                passwordEncoder, jwtService, employeeRepository,
                adminRepository, eventPublisher);
        token = new VerificationToken();
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
    void whenEmailDoesNotMatch() {
        // given
        final String email = "c.e@email.com";
        final String confirmEmail = "ce@email.com";
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setConfirmEmail(confirmEmail);
        registerRequest.setType(UserType.EMPLOYEE.toString());
        when(userRepository.existsByEmailAddress(email)).thenReturn(false);
        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> underTest.registerNewCustomer(registerRequest, null));
    }

    @Test
    void whenSuccessfulEmailConfirmation() {
        // given
        String tokenValue = "yJhb6IkpXVCJ9.eyJzdWIiOiIx4I6MTUxNjIzOTAyMn0.tyh-VfuzIxCyGYDlkB";
        User user = new User();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 6);
        token.setToken(tokenValue);
        token.setUser(user);
        token.setExpiryDate(cal.getTime());
        when(tokenRepository.findByToken(tokenValue)).thenReturn(token);

        // when
        String result = underTest.validateVerificationToken(tokenValue);

        // then
        Assertions.assertEquals(TokenStatus.VALIDTOKEN.value(), result);
    }

    @Test
    void whenFailedEmailConfirmationTokenInvalid() {
        // given
        String tokenValue = "yJhb6IkpXVCJ9.eyJzdWIiOiIx4I6MTUxNjIzOTAyMn0.tyh-VfuzIxCyGYDlkB";
        // when
        // token couldn't be found in db
        when(tokenRepository.findByToken(tokenValue)).thenReturn(null);
        // then
        Assertions.assertEquals(TokenStatus.INVALIDTOKEN.value(),
                underTest.validateVerificationToken(tokenValue));
    }

    @Test
    void whenFailedEmailConfirmationTokenExpired() {
        // given
        String tokenValue = "yJhb6IkpXVCJ9.eyJzdWIiOiIx4I6MTUxNjIzOTAyMn0.tyh-VfuzIxCyGYDlkB";
        User user = new User();
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_WEEK, -1);
        cal.set(Calendar.DAY_OF_WEEK, cal.get(Calendar.DAY_OF_WEEK) - 1);
        token.setToken(tokenValue);
        token.setUser(user);
        token.setExpiryDate(cal.getTime());
        when(tokenRepository.findByToken(tokenValue)).thenReturn(token);

        // when
        String result = underTest.validateVerificationToken(tokenValue);

        // then
        Assertions.assertEquals(TokenStatus.EXPIREDTOKEN.value(), result);
    }


    @Test
    void loginUser() {
    }
}