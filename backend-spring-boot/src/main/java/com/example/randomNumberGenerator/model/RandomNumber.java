package com.example.randomNumberGenerator.model;

public class RandomNumber {
	
	private long id;
	private int number;
	private String timestamp;
	
	public RandomNumber() {
		
	}
	
	public RandomNumber(long id, int number, String timestamp) {
		//super();
		this.id = id;
		this.number = number;
		this.timestamp = timestamp;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
