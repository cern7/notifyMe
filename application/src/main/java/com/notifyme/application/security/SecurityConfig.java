package com.notifyme.application.security;

import com.notifyme.application.exception.AccessDeniedHandlerImpl;
import com.notifyme.application.exception.AuthExceptionHandler;
import com.notifyme.application.exception.DefaultExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    private final AuthExceptionHandler authExceptionHandler;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    // add exception handler for unauthorized access and
    // access denied

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          AuthTokenFilter authenticationJwtTokenFilter,
                          AuthExceptionHandler authExceptionHandler,
                          AccessDeniedHandlerImpl accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
        this.authExceptionHandler = authExceptionHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/service/addService").hasAnyAuthority("ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//                .and()
//                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/service/all").authenticated()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/service/book").hasAnyAuthority("CUSTOMER")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/service/addEmployeeToService/*").hasAnyAuthority("ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/booking/customer/all/*").hasAnyAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authExceptionHandler).accessDeniedHandler(accessDeniedHandler);

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
