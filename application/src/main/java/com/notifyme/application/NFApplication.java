package com.notifyme.application;

import com.notifyme.application.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronExpression;

import java.time.*;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableScheduling
public class NFApplication {

    public static void main(String[] args) {
        SpringApplication.run(NFApplication.class, args);
//        CronExpression ex = CronExpression.parse("0 25 8 * * *");
//        LocalDateTime res = ex.next(LocalDateTime.now());
//        System.out.println(res);
    }


}
