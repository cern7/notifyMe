package com.notifyme.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NFApplication {

	public static void main(String[] args) {
		SpringApplication.run(NFApplication.class, args);


//		String phone = "+40255570090";
//		String regex ="^\\+40-7[0-9]{2}-[0-9]{3}-[0-9]{3}$";
//		System.out.println(Pattern.matches(regex, phone));
	}

}
