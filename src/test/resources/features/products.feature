Feature: Product API validation

  @products @smoke
  Scenario: Verify all products can be retrieved
    When I send a GET request to get all products
    Then the response status code should be 200
    And the response should contain product details
    And the product response should have valid fields

  @products
  Scenario Outline: Verify searching for a product returns matching results
    When I send a POST request to search for product "<searchTerm>"
    Then the response status code should be 200
    And the response should contain searched products
    And each product in the search results should be related to "<searchTerm>"

    Examples:
      | searchTerm |
      | top        |
      | tshirt     |
      | jean       |

  @products @negative
  Scenario: Verify POST request to all products list is not allowed
    When I send a POST request to all products list
    Then the response status code should be 200
    And the response code in body should be 405
    And the response message should be "This request method is not supported."
