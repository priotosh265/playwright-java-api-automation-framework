Feature: Brands API validation

  @brands @smoke
  Scenario: Verify all brands can be retrieved
    When I send a GET request to get all brands
    Then the response status code should be 200
    And the response should contain brand details
    And each brand should have a valid id and brand name

  @brands @negative
  Scenario: Verify PUT request to all brands list is not allowed
    When I send a PUT request to all brands list
    Then the response status code should be 200
    And the response code in body should be 405
    And the response message should be "This request method is not supported."
