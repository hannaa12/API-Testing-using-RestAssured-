package com.fdmgroup.pojos;

public class Food {
	private String id;
	private String name;
	private double price;
	
	public Food() {
		super();
	}
	
	public Food(String id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	public Food(double price) {
		super();
		this.price = price;
	}

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
}
