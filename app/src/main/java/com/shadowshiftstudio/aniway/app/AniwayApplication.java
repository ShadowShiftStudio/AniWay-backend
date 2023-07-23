package com.shadowshiftstudio.aniway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shadowshiftstudio.aniway")
public class AniwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AniwayApplication.class, args);
	}

}
