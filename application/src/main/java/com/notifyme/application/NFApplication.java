package com.notifyme.application;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
@EnableScheduling
public class NFApplication {
    @Value("${allow.domain}")
    private String allowDomain;

    public static void main(String[] args) {
        SpringApplication.run(NFApplication.class, args);

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry
                        .addMapping("/api/**")
                        .allowedOrigins(allowDomain)
                        .allowedMethods("POST", "GET");
                System.out.println(allowDomain + "/api/");
            }
        };
    }

}
