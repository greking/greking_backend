package com.greking.Greking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GrekingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrekingApplication.class, args);
	}

}
