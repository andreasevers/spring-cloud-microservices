package com.example;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableMicroservicesDashboardServer
@SpringBootApplication
public class MsdashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsdashboardApplication.class, args);
	}
}
