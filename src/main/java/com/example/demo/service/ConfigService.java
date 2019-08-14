package com.example.demo.service;

import reactor.core.publisher.Mono;

public interface ConfigService {
	Mono<String> fetchConfigCount();
}
