package com.example;

import com.jayway.jsonpath.JsonPath;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class CarmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarmsApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@RestController
class CarController {

	private RestTemplate restTemplate;

	@Autowired
	CarController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/car")
	public String getCar() {
		String colour = getColour();
		return "BMW with colour: " + colour;
	}

	@HystrixCommand(fallbackMethod = "fallbackColour")
	private String getColour() {
		return restTemplate.getForObject("http://colourms/colour", String.class);
	}

	private String fallbackColour() {
		return "blue";
	}

	@Component("colourms")
	class ColourServiceHealth extends AbstractHealthIndicator {

		private RestTemplate restTemplate;

		@Autowired
		ColourServiceHealth(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@Override
		protected void doHealthCheck(Builder builder) throws Exception {
			String json = restTemplate.getForObject("http://colourms/health", String.class);
			builder.status((String) JsonPath.read(json, "$.status"));
		}
	}
}
