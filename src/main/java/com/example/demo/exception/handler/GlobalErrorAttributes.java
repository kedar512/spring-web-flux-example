package com.example.demo.exception.handler;

import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.example.demo.exception.ConfigNotFoundException;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes{
    
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private String message = "Unknown error";

    public GlobalErrorAttributes() {
        super(false); 
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
        
        Throwable t = super.getError(request);
        
        if (t instanceof ConfigNotFoundException) {
        	map.put("status", HttpStatus.OK);
            map.put("message", "Config details not found");
        } else {
        	map.put("status", getStatus());
            map.put("message", getMessage());
        }
        return map;
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
