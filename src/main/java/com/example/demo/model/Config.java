package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("test")
public class Config {
	
	@Id
	private String id;
	
	@Field("count")
	private String count;
	
	@Field("data_types")
	private List<DataType> dataTypes;

	public List<DataType> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<DataType> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
