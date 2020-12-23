package com.example.randomNumberGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.example.randomNumberGenerator.*" })
public class RandomNumberGeneratorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomNumberGeneratorBackendApplication.class, args);
	}

}
