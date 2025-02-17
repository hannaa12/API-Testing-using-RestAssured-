package com.fdmgroup.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.pojos.Manager;
import com.fdmgroup.pojos.Staff;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class F3_ManagersTest_StepDef {
	
	Response response = F1_FoodsTest_StepDef.response;
	int initialStaffCount;
	
	@When("User sends a PUT request to {string} with the following staff list:")
	public void user_sends_a_put_request_to_with_the_following_staff_list(String endpointId, String staffs) throws JsonMappingException, JsonProcessingException {
	    
		Manager manager = response.as(Manager.class);
		
	    ObjectMapper objectMapper = new ObjectMapper(); 

        List<Staff> staffsList = objectMapper.readValue(staffs, new TypeReference<List<Staff>>() {});

		manager.setStaffs(staffsList);
		
		given()
			.contentType(ContentType.JSON)
			.log().all()
		.with()
			.body(manager)
		.when()
			.put(endpointId);
		}

	@Then("The staff list under manager {string} should include [{string}, {string}, {string}]")
	public void the_staff_list_under_manager_should_include(String id, String name1, String name2, String name3) {
	    
		response = given()
		        .contentType(ContentType.JSON)
		        .log().all()
		    .when()
		        .get("/managers/" + id) 
		       .then()
		        .extract().response();
	
	Manager retrievedManager = response.as(Manager.class);
	
	List<String> actualStaffNames = retrievedManager.getStaffs().stream()
            .map(Staff::getName)
            .collect(Collectors.toList());
	
    assertThat(actualStaffNames, containsInAnyOrder(name1 , name2, name3));
	}

	@When("User sends a DELETE request to {string} and staff id {int}")
	public void user_sends_a_delete_request_to_and_staff_id(String managerId, Integer staffId) {
		
		 Manager manager = response.as(Manager.class);
		    initialStaffCount = manager.getStaffs().size();

		    List<Staff> updatedStaffList = manager.getStaffs().stream()
		            .filter(staff -> staff.getId() != staffId)
		            .collect(Collectors.toList());
		    manager.setStaffs(updatedStaffList);

		    given()
	        .contentType(ContentType.JSON)
	        .log().all()
	        .with()
	            .body(manager)
	        .when()
	            .put(managerId)
	        .then()
	            .assertThat()
	                .header("Content-Type", containsString("application/json"))
	                .statusCode(200);
		    
		    assertThat(manager.getStaffs().stream()
		            .noneMatch(staff -> staff.getId() == staffId), is(true));
	}

	@Then("The number of staffs in the list under manager {string} should be correct")
	public void the_number_of_staffs_in_the_list_under_manager_should_be_correct(String managerId) {
	   
		response = given()
	            .contentType(ContentType.JSON)
	            .log().all()
	        .when()
	            .get("/managers/" + managerId)
	        .then()
	            .assertThat()
	                .statusCode(200)
	            .extract().response();

	    Manager updatedManager = response.as(Manager.class);
	   
		int expectedStaffCount = initialStaffCount - 1; 
	    assertThat(updatedManager.getStaffs().size(), equalTo(expectedStaffCount));
	        
	}

}
