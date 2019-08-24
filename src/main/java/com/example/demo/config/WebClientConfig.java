package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClient() {
		return WebClient
				.builder()
				.baseUrl("http://localhost:9055")
				.clientConnector(configureHttpConnector())
				.build();
	}
	
	private ReactorClientHttpConnector configureHttpConnector() {
		TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(10))
                                  .addHandlerLast(new WriteTimeoutHandler(10)));
		return new ReactorClientHttpConnector(HttpClient.from(tcpClient));
	}
}
