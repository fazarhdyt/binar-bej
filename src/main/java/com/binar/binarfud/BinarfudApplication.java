package com.binar.binarfud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class BinarfudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinarfudApplication.class, args);
	}

}
