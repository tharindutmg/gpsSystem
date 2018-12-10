package com.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GpsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpsapiApplication.class, args);
	}
}
