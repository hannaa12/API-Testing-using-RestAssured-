# Author: Hani Saravanan
# Date: Oct. 24, 2024
@CommentsTest
Feature: As a user, I want to interact with the food, comment and manager API to create, update, retrieve, and delete food items, comments and managers staff.

  Background: 
    Given The API base URL is set to "http://localhost:3000"

  @CreateComment
  Scenario: User creates a new comment for a food item
    When User sends a POST request to "/comments" with the following body:
      """
      {
        "id": "5",
        "userId": 5,
        "body": "Great taste!",
        "foodId": 2
      }
      """
    Then User should see the response status code 201
    And The response should contain the created comment with id "5", userId 5, body "Great taste!", and foodId 2

  @DeleteComment
  Scenario: User deletes a comment by id
    Given A "comments" item exists with id "5"
    When User sends a DELETE request to "/comments/5"
    Then User should see the response status code 200
    And The comment with id "5" should no longer exist

  @GetCommentByUserIdAndFoodId
  Scenario: User retrieves a comment using userId and foodId
    When User sends a GET request to "/comments"
    Then User should see the response status code 200
    And The comment body for userId 2 and foodId 1 should be "well done"
