package com.example.demo.service;

import com.example.demo.model.Config;

import reactor.core.publisher.Mono;

public interface ConfigService {
	Mono<String> fetchConfigCount(String config);
	
	Mono<Config> fetchConfigDetails();
}
