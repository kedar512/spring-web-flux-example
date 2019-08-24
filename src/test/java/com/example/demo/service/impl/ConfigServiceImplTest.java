package com.example.demo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Config;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceImplTest {

	@Autowired
	ConfigServiceImpl configServiceImpl;

	@Autowired
	ReactiveMongoTemplate template;
	
	@Before
	public void setUp() {
		Config config = new Config();
		
		config.setId("5d54512075d2b91ee7065a30");
		config.setCount("1000");

		StepVerifier.create(template.dropCollection(Config.class)).verifyComplete();

		Flux<Config> insertAll = template
				.insertAll(Flux.just(config).collectList());

		StepVerifier.create(insertAll).expectNextCount(1).verifyComplete();
	}

	@Test
	public void testFetchConfigCountNotFound() {
		StepVerifier.create(configServiceImpl.fetchConfigCount("test"))
				.expectSubscription()
				.assertNext(res -> Boolean.FALSE.equals(res.contains("count")))
				.verifyComplete();
	}
	
	@Test
	public void testFetchConfigCountSuccess() {
		StepVerifier.create(configServiceImpl.fetchConfigCount("count"))
				.expectSubscription()
				.expectNext("{ \"_id\" : { \"$oid\" : \"5d54512075d2b91ee7065a30\" }, \"count\" : \"1000\" }")
				.verifyComplete();
	}

}
