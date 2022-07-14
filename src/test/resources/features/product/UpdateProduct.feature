Feature: User updates the data of a product

  Scenario: Update product works as expected
    Given a valid product ID
    And a valid input product
    And a product repository with products
    When the user updates the product
    Then the product is successfully updated

  Scenario: Update product fails due to an invalid product ID
    Given an invalid product ID
    And a valid input product
    And a product repository with products
    When the user updates the product
    Then update product fails with bad request error

  Scenario: Update product fails due to an invalid input product
    Given an invalid input product
    And a product repository with products
    When the user creates the product
    Then update product fails with bad request error

  Scenario: Update product fails due to an input product with invalid properties
    Given an input product with invalid properties
    And a product repository with products
    When the user creates the product
    Then update product fails with bad request error

  Scenario: Update product fails due to a non existent product ID
    Given a non existent product ID
    And a valid input product
    And a product repository with products
    When the user updates the product
    Then update product fails with product not found error