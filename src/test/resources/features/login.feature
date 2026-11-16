Feature: Login API validation

  @login @smoke
  Scenario: Verify login with invalid credentials returns user not found
    When I send a POST request to verify login with email "nonexistent_playwright_user_9999@example.com" and password "wrongPasswordXYZ"
    Then the response status code should be 200
    And the response code in body should be 404
    And the response message should be "User not found!"

  @login @negative
  Scenario: Verify login without email parameter returns bad request
    When I send a POST request to verify login with password "Password123" but missing email
    Then the response status code should be 200
    And the response code in body should be 400
    And the response message should be "Bad request, email or password parameter is missing in POST request."

  @login @negative
  Scenario: Verify login without password parameter returns bad request
    When I send a POST request to verify login with email "test@example.com" but missing password
    Then the response status code should be 200
    And the response code in body should be 400
    And the response message should be "Bad request, email or password parameter is missing in POST request."

  @login @negative
  Scenario: Verify DELETE request to verify login is not supported
    When I send a DELETE request to verify login
    Then the response status code should be 200
    And the response code in body should be 405
    And the response message should be "This request method is not supported."
