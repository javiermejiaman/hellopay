package com.jm.hellopay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HelloPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloPayApplication.class, args);
	}

}
