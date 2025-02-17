# Author: Hani Saravanan
# Date: Oct. 24, 2024
@ManagersTest
Feature: As a user, I want to interact with the food, comment and manager API to create, update, retrieve, and delete food items, comments and managers staff.

  Background: 
    Given The API base URL is set to "http://localhost:3000"

  @UpdateStaffUnderManager
  Scenario: User updates the staff list under a manager
    Given A "managers" item exists with id "1"
    When User sends a PUT request to "/managers/1" with the following staff list:
      """
      [
        { "id": 4, "name": "Hani Saravanan", "salary": 450 },
        { "id": 5, "name": "Ben Lee", "salary": 275 },
        { "id": 6, "name": "Simon Adams", "salary": 320 }
      ]
      """
    Then User should see the response status code 200
    And The staff list under manager "1" should include ["Hani Saravanan", "Ben Lee", "Simon Adams"]

  @DeleteStaffFromManager
  Scenario: User deletes a staff under a manager
    Given A "managers" item exists with id "1"
    When User sends a DELETE request to "/managers/1" and staff id 6
    Then User should see the response status code 200
    And The number of staffs in the list under manager "1" should be correct
