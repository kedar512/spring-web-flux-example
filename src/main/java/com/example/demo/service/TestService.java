package com.example.demo.service;

import com.example.demo.model.User;

import reactor.core.publisher.Mono;

public interface TestService {
	Mono<User> test();
}
