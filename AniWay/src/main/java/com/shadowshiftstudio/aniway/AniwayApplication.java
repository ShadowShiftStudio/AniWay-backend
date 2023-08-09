package com.shadowshiftstudio.aniway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan( basePackages = {"com.shadowshiftstudio.aniway.entity.user"} )
public class AniwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AniwayApplication.class, args);
    }

}
