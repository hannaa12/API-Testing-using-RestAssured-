package com.fdmgroup.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
	private String id;
	
	@JsonProperty("userid") 
	private int userid;
	
	private String body;
	private int foodId;
	
	public Comment() {
		super();
	}
	
	public Comment(String id, int userid, String body, int foodId) {
		super();
		this.id = id;
		this.userid = userid;
		this.body = body;
		this.foodId = foodId;
	}
	public String getId() {
		return id;
	}
	public int getUserId() {
		return userid;
	}
	public String getBody() {
		return body;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(int userid) {
		this.userid = userid;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	
	
	
}
