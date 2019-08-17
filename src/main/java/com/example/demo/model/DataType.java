package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("test")
public class DataType {
	
	@Field("first_type")
	private String firstType;
	
	@Field("second_type")
	private String secondType;

	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}
}
