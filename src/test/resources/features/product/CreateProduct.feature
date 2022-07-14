Feature: User creates a product

  Scenario: Create product works as expected
    Given a valid input product
    And a product repository with products
    When the user creates the product
    Then the product is successfully created

  Scenario: Create product fails due to an invalid input product
    Given an invalid input product
    And a product repository with products
    When the user creates the product
    Then create product fails with bad request error

  Scenario: Create product fails due to an input product with invalid properties
    Given an input product with invalid properties
    And a product repository with products
    When the user creates the product
    Then create product fails with bad request error