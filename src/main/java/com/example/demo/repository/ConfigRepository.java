package com.example.demo.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Config;

import reactor.core.publisher.Mono;

@Repository
public interface ConfigRepository extends ReactiveCrudRepository<Config, String> {
	@Query(value = "{ 'count' : ?0, 'dataTypes.firstType' : ?1 }", fields = "{ 'dataTypes.firstType' : 1, 'count' : 1 }")
	Mono<Config> findByCountAndDataType(String count, String dataType);
}
