package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ConfigNotFoundException;
import com.example.demo.service.ConfigService;

import reactor.core.publisher.Mono;

@Service
public class ConfigServiceImpl implements ConfigService {
	
	@Autowired
	ReactiveMongoTemplate template;

	@Override
	public Mono<String> fetchConfigCount() {
		Query query = new Query();
		
		query.fields().include("count");
		query.addCriteria(Criteria.where("_id").is("5d54512075d2b91ee7065a30"));
		
		return template.findOne(query, String.class, "test")
				.switchIfEmpty(Mono.error(new ConfigNotFoundException("Config details not found")));
	}

}
