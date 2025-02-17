# Author: Hani Saravanan
# Date: Oct. 24, 2024
@FoodsTest
Feature: As a user, I want to interact with the food, comment and manager API to create, update, retrieve, and delete food items, comments and managers staff.

  Background: 
    Given The API base URL is set to "http://localhost:3000"

  @GetAllFoods
  Scenario: User retrieves the list of all foods
    When User sends a GET request to "/foods"
    Then User should see the response status code 200
    And The response header should contain "Content-Type" with "application/json"
    And The response should contain a list of foods with ids ["1", "2", "3", "4"]
    And The food names should include ["cucumber salad", "french fries", "soft drink", "burgers"]

  @CreateFoodItem
  Scenario: User creates a new food item
    When User sends a POST request to "/foods" with the following body:
      """
      {
        "id": "5",
        "name": "spaghetti",
        "price": 12.99
      }
      """
    Then User should see the response status code 201
    And The response should contain the created food with id "5", name "spaghetti", and price 12.99

  @UpdateFoodPrice
  Scenario: User updates the price of a food item
    Given A "foods" item exists with id "2"
    When User sends a PUT request to "/foods/2" with the updated price 7.99
    Then User should see the response status code 200
    And The updated food price should be 7.99
