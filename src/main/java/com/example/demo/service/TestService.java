package com.example.demo.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.model.User;

import reactor.core.publisher.Mono;

public interface TestService {
	Mono<User> test();
	
	Mono<ResponseEntity<String>> testServiceCall(String uri, Map<String, String> headers,
			Map<String, String> queryParams);
}
