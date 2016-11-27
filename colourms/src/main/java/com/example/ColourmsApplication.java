package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class ColourmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColourmsApplication.class, args);
	}
}

@RestController
class ColourController {

	@Value("${service.colour}")
	private String colour;

	@GetMapping("/colour")
	public String getColour() {
		return colour;
	}
}
