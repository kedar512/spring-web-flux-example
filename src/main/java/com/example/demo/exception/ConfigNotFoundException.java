package com.example.demo.exception;

public class ConfigNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public ConfigNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public ConfigNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
