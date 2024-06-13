package com.example.pensionMatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PensionMatchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PensionMatchingApplication.class, args);
	}

}
