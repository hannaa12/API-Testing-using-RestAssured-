package com.fdmgroup.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;

import com.fdmgroup.pojos.Food;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class F1_FoodsTest_StepDef {
	
	public static Response response;
	
	
	@Given("The API base URL is set to {string}")
	public void the_api_base_url_is_set_to(String baseUrl) {
		
		 RestAssured.baseURI = baseUrl;
	}

	@When("User sends a GET request to {string}")
	public void user_sends_a_get_request_to(String endpoint) {
	
		response = given()
	            .contentType("application/json")
	            .log().all()
	        .when()
	            .get(endpoint)
	        .then()
	            .extract().response();
	}

	@Then("User should see the response status code {int}")
	public void user_should_see_the_response_status_code(Integer expectedStatusCode) {
		
		assertThat(response.statusCode(), equalTo(expectedStatusCode));
	}

	@Then("The response header should contain {string} with {string}")
	public void the_response_header_should_contain_with(String headerName, String expectedHeaderValue) {
		String actualHeaderValue = response.getHeader(headerName);
        assertThat(actualHeaderValue, equalTo(expectedHeaderValue));
	}
    

	@Then("The response should contain a list of foods with ids [{string}, {string}, {string}, {string}]")
	public void the_response_should_contain_a_list_of_foods_with_ids(String id1, String id2, String id3, String id4) {
		
		// Extract list of foods from the response as a List of Maps or as a list of Food objects
        List<String> actualIds = response.jsonPath().getList("id");

        // Assert that the list of actual ids contains all the expected ids, regardless of order
        assertThat(actualIds, containsInAnyOrder(id1, id2, id3, id4));
	}

	@Then("The food names should include [{string}, {string}, {string}, {string}]")
	public void the_food_names_should_include(String name1, String name2, String name3, String name4) {
	    
		List<String> actualIds = response.jsonPath().getList("name");

        // Assert that the list of actual ids contains all the expected ids, regardless of order
        assertThat(actualIds, containsInAnyOrder(name1, name2, name3, name4));
	}
	@When("User sends a POST request to {string} with the following body:")
	public void user_sends_a_post_request_to_with_the_following_body(String endpoint, String requestBody) {
		
		response = RestAssured
		        .given()
		        .contentType("application/json")  
		        .body(requestBody)              
		        .when()
		        .post(endpoint); 
	    
	}

	@Then("The response should contain the created food with id {string}, name {string}, and price {float}")
	public void the_response_should_contain_the_created_food_with_id_name_and_price(String id, String name, float price) {
	    
		// Extract the created food from the response
	    Map<String, Object> createdFood = response.jsonPath().getMap("$");

	    // Verify the food's attributes
	    assertThat("Food ID mismatch", createdFood.get("id"), is(id));
	    assertThat("Food name mismatch", createdFood.get("name"), is(name));
	    assertThat("Food price mismatch", createdFood.get("price"), is(price));
		}

	
	@Given("A {string} item exists with id {string}")
	public void a_item_exists_with_id(String endpoint, String id) {
		response = given()
		        .contentType(ContentType.JSON)
		        .log().all()
		    .when()
		        .get("/" + endpoint+ "/" + id)  
		    .then()
		        .assertThat()
		            .statusCode(200)  
		        .extract().response();
	}

	@When("User sends a PUT request to {string} with the updated price {double}")
	public void user_sends_a_put_request_to_with_the_updated_price(String endpoint, double updatedPrice) {
		Food food = response.as(Food.class);
		food.setPrice(updatedPrice);
		
		 response = given()
	                .contentType("application/json")
	                .body(food)
	                .when()
	                .put(endpoint);
	}

	@Then("The updated food price should be {double}")
	public void the_updated_food_price_should_be(double expectedPrice) {
	    
		double actualPrice = response.jsonPath().getDouble("price");


        assertThat("Updated food price mismatch", actualPrice, is(expectedPrice));
	}


}
