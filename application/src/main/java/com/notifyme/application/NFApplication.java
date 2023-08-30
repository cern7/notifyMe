package com.notifyme.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NFApplication {

    public static void main(String[] args) {
        SpringApplication.run(NFApplication.class, args);
    }


}
