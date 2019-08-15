package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ConfigService;

import reactor.core.publisher.Mono;

@RestController
public class ConfigController {
	
	@Autowired
	ConfigService configService;
	
	@GetMapping("/config")
	public Mono<String> getConfig() {
		return configService.fetchConfigCount("count");
	}
}
