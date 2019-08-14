package com.example.demo.service.impl;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.exception.ConfigNotFoundException;
import com.example.demo.exception.handler.GlobalErrorWebExceptionHandler;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
//@WebFluxTest(ConfigService.class)
@SpringBootTest
public class ConfigServiceImplTest {
	
	@Autowired
	ConfigServiceImpl configServiceImpl;
	
	@MockBean
	ReactiveMongoTemplate template;
	
	@MockBean
	GlobalErrorWebExceptionHandler handler;
	
	/*
	 * @Test public void testFetchConfigCountNotFound() {
	 * when(template.findOne(Mockito.any(Query.class), Mockito.any(),
	 * Mockito.anyString())).thenReturn(Mono.empty());
	 * 
	 * StepVerifier.create(configServiceImpl.fetchConfigCount())
	 * .expectSubscription() .expectError(ConfigNotFoundException.class) .verify();
	 * }
	 */
	
}
