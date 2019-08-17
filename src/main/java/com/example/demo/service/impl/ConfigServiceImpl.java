package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ConfigNotFoundException;
import com.example.demo.model.Config;
import com.example.demo.repository.ConfigRepository;
import com.example.demo.service.ConfigService;

import reactor.core.publisher.Mono;

@Service
public class ConfigServiceImpl implements ConfigService {
	
	@Autowired
	ReactiveMongoTemplate template;
	
	@Autowired
	ConfigRepository configRepo;
	
	@Value("${mongo.config.document.id}")
	private String documentId;

	@Override
	public Mono<String> fetchConfigCount(String config) {
		Query query = new Query();
		
		query.fields().include(config);
		query.addCriteria(Criteria.where("_id").is(documentId));
		
		return template.findOne(query, String.class, "test")
				.switchIfEmpty(Mono.error(new ConfigNotFoundException("Config details not found")));
	}

	@Override
	public Mono<Config> fetchConfigDetails() {
		return configRepo.findByCountAndDataType("1000", "String");
	}

}
