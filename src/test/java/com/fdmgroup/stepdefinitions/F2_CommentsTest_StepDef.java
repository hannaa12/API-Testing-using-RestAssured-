package com.fdmgroup.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.List;

import com.fdmgroup.pojos.Comment;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class F2_CommentsTest_StepDef {
	
	Response response = F1_FoodsTest_StepDef.response;

	@Then("The response should contain the created comment with id {string}, userId {int}, body {string}, and foodId {int}")
	public void the_response_should_contain_the_created_comment_with_id_user_id_body_and_food_id(String id, Integer userid, String body, Integer foodId) {
	    
		Comment createdComment = response.as(Comment.class); 
	    assertThat(createdComment.getId(), equalTo(id));
	    assertThat(createdComment.getUserId(), equalTo(userid));
	    assertThat(createdComment.getBody(), equalTo(body));
	    assertThat(createdComment.getFoodId(), equalTo(foodId));
	}


	@When("User sends a DELETE request to {string}")
	public void user_sends_a_delete_request_to(String endpointId) {
		
		response = given()
		.contentType(ContentType.JSON)
	.when()
		.delete(endpointId);
	}

	@Then("The comment with id {string} should no longer exist")
	public void the_comment_with_id_should_no_longer_exist(String id) {
		
		given()
        .contentType(ContentType.JSON)
        .log().all()
    .when()
        .get("/comments/" + id)  
    .then()
        .assertThat()
            .statusCode(404);	
	}

	@Then("The comment body for userId {int} and foodId {int} should be {string}")
	public void the_comment_body_for_user_id_and_food_id_should_be(Integer userid, Integer foodId, String body) {
		
		List<Comment> comments = response.jsonPath().getList("", Comment.class);
		assertThat(comments, is(not(empty())));
		
		
		Comment relevantComment = comments.stream()
			    .filter(comment -> comment.getUserId() == userid && comment.getFoodId() == foodId)
			    .findFirst()
			    .orElseThrow(() -> new AssertionError("No comment found for userId: " + userid + " and foodId: " + foodId));

		
		assertThat(relevantComment.getBody(), equalTo(body));
	}
}
