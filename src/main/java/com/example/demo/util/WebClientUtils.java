package com.example.demo.util;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class WebClientUtils {
	
	private WebClient webClient;
	
	@Autowired
	public WebClientUtils(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public Mono<ResponseEntity<String>> callGetApi(String uri, Map<String, String> headers,
			MultiValueMap<String, String> queryParams) {
		return webClient.get()
		.uri(uriBuilder -> uriBuilder
			    .path(uri)
			    .queryParams(queryParams)
			    .build())
		.headers(h -> h.setAll(headers))
		.exchange()
		.flatMap(res -> {
			return res.bodyToMono(String.class)
				.flatMap(body -> {
					return Mono.just(new ResponseEntity<>(body, HttpStatus.OK));
				});
		}).onErrorResume(error -> {
			JSONObject jsonObj = new JSONObject();

			jsonObj.put("message", "Error in calling service");
			jsonObj.put("statusCode", 0);
			
			return Mono.just(new ResponseEntity<>(jsonObj.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		});
	}
}
