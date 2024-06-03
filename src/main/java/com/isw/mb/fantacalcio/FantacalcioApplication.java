package com.isw.mb.fantacalcio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class FantacalcioApplication {

	public static void main(String[] args) {

		SpringApplication.run(FantacalcioApplication.class, args);

		System.out.println("Click here http://localhost:8080 to access the application");

	}

}
