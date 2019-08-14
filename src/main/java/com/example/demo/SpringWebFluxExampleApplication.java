package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringWebFluxExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxExampleApplication.class, args);
	}

}
