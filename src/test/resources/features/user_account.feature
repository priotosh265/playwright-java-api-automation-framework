Feature: User Account API validation

  @user @lifecycle
  Scenario: Verify complete user account lifecycle: create, get details, and delete account
    When I create a new user account with unique email
    Then the response status code should be 200
    And the response code in body should be 201
    And the response message should be "User created!"
    When I get user detail by the created email
    Then the response status code should be 200
    And the response code in body should be 200
    And the user detail response should match the created user name
    When I delete the created user account
    Then the response status code should be 200
    And the response code in body should be 200
    And the response message should be "Account deleted!"

  @user @negative
  Scenario: Verify retrieving details for non-existent user email returns 404
    When I get user detail by email "nonexistent_playwright_user_9999@example.com"
    Then the response status code should be 200
    And the response code in body should be 404
    And the response message should be "Account not found with this email, try another email!"

  @user @negative
  Scenario: Verify deleting non-existent user account returns 404
    When I send a DELETE request to delete account with email "nonexistent_playwright_user_9999@example.com" and password "wrongPasswordXYZ"
    Then the response status code should be 200
    And the response code in body should be 404
    And the response message should be "Account not found!"
