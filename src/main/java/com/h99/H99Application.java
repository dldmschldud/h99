package com.h99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class H99Application {

    public static void main(String[] args) {
        SpringApplication.run(H99Application.class, args);
    }

}
