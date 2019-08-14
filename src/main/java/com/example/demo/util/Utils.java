package com.example.demo.util;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Utils {
	
	public static Mono<String> testUtility() {
		Mono<String> blockingWrapper = Mono.fromCallable(() -> { 
		    return "test"; 
		});
		return blockingWrapper.subscribeOn(Schedulers.elastic());
	}
	
	public static Mono<String> testBlockingWrapper() {
		Mono<String> blockingWrapper = Mono.fromCallable(() -> { 
		    return "Blocking wrapper"; 
		});
		return blockingWrapper.subscribeOn(Schedulers.elastic());
	}
}
