package com.example.demo.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TestService;
import com.example.demo.util.Utils;
import com.example.demo.util.WebClientUtils;

import reactor.core.publisher.Mono;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	WebClientUtils webClientUtils;

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

	@Override
	public Mono<ResponseEntity<String>> testServiceCall(String uri, Map<String, String> headers,
			Map<String, String> queryParams) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		queryParams.entrySet().stream().forEach(q -> params.add(q.getKey(), q.getValue()));
		return webClientUtils.callGetApi(uri, headers, params);
	}

}
