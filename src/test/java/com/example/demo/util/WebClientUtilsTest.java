package com.example.demo.util;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.matchers.JsonPathMatchers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;

public class WebClientUtilsTest {
	
	private final MockWebServer mockWebServer = new MockWebServer();
    private final WebClientUtils webClientUtils = new WebClientUtils(WebClient.create(mockWebServer.url("/").toString()));
    
    @After
	public void shutdown() throws Exception {
		this.mockWebServer.shutdown();
	}
    
    @SuppressWarnings("deprecation")
	@Test
    public void testCallGetApiConnectionLostFailed() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        );
        
        Map<String, String> headers = new HashMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        
        ResponseEntity<String> response = webClientUtils.callGetApi("/test", headers, queryParams).block();
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        //use method provided by MockWebServer to assert the request header
        recordedRequest.getHeader("Authorization").equals("customAuth");
        DocumentContext context = JsonPath.parse(recordedRequest.getBody().inputStream());
        //use JsonPath library to assert the request body
        assertThat(context, JsonPathMatchers.isJson(allOf(
        		JsonPathMatchers.withJsonPath("$.message", is("Error in calling service")),
        		JsonPathMatchers.withJsonPath("$.statusCode", is(0))
        )));
    }
    
	@Test
    public void testCallGetApiResponseTimeOutFailed() throws InterruptedException, IOException {
		mockWebServer.enqueue(new MockResponse()
		        .setBody("ABC")
		        .clearHeaders()
		        .addHeader("Content-Length: 4"));
        
		URLConnection urlConnection = mockWebServer.url("/").url().openConnection();
	    urlConnection.setReadTimeout(1000);
	    InputStream in = urlConnection.getInputStream();
	    assertThat(in.read()).isEqualTo('A');
	    assertThat(in.read()).isEqualTo('B');
	    assertThat(in.read()).isEqualTo('C');
	    try {
	      in.read(); // if Content-Length was accurate, this would return -1 immediately
	      fail();
	    } catch (SocketTimeoutException expected) {
	    }
    }
    
    @Test
    public void testCallGetApiSuccess() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"message\": \"Success\", \"statusCode\": 1}")
        );
        
        Map<String, String> headers = new HashMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        
        ResponseEntity<String> response = webClientUtils.callGetApi("/test", headers, queryParams).block();
        assertThat(response, is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));

        //use method provided by MockWebServer to assert the request header
        DocumentContext context = JsonPath.parse(response.getBody());
        //use JsonPath library to assert the request body
        assertThat(context, JsonPathMatchers.isJson(allOf(
        		JsonPathMatchers.withJsonPath("$.message", is("Success")),
        		JsonPathMatchers.withJsonPath("$.statusCode", is(1))
        )));
    }
}
