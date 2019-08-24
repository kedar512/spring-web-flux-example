package com.example.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TestService;
import com.example.demo.util.Utils;

import reactor.core.publisher.Mono;

@RestController
public class TestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "test", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<String> test() {
		LOGGER.info("TestController --> test() --> start");
		Mono<String> testStr = Utils.testBlockingWrapper();
		
		return testStr.flatMap(e -> {
			if (null == e) {
				return Mono.just("test null");
			} else {
				Mono<User> test = testService.test();
				
				return test.defaultIfEmpty(defaultUser())
						.flatMap(t -> {
							if (null == t) {
								return Mono.just("test null");
							} else {
								return Mono.just(t.getName());
							}
						});
					}
			}).flatMap(e -> {
				if ("Kedar".equals(e)) {
					return Mono.just("It works");
				} else {
					return Mono.just("It still works");
				}
			});
	}
	
	@GetMapping(value = "testwebclient", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<String>> testWebClient(@RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) {
		LOGGER.info("TestController --> testWebClient() --> start");
		return testService.testServiceCall("/test", headers, queryParams);
	}
	
	private User defaultUser() {
		User user = new User();
		
		user.setName("Not Found");
		
		return user;
	}
}
