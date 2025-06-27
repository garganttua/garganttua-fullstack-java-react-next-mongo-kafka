package com.garganttua.noghost.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"com.garganttua", "com.garganttua"})
@Configuration
@SpringBootApplication
@EnableScheduling
public class BackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
