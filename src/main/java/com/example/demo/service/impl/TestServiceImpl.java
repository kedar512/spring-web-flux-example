package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TestService;
import com.example.demo.util.Utils;

import reactor.core.publisher.Mono;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Mono<User> test() {
		Mono<String> testStr = Utils.testUtility();
		
		return testStr.flatMap(e -> {
			if (null == e) {
				throw new RuntimeException();
			} else {
				return userRepository.findByName("Kedar");
			}
		});
	}

}
