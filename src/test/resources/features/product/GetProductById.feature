Feature: User requests data about a product

  Scenario: Get product by ID works as expected
    Given a valid product ID
    And a product repository with products
    When the user retrieves the product
    Then the product is successfully retrieved

  Scenario: Get product by ID fails due to an invalid product ID
    Given an invalid product ID
    And a product repository with products
    When the user retrieves the product
    Then get product by ID fails with bad request error

  Scenario: Get product by ID fails due to a non existent product ID
    Given a non existent product ID
    And a product repository with products
    When the user retrieves the product
    Then get product by ID fails with product not found error