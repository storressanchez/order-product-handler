Feature: User requests data about all products

  Scenario: Get product list works as expected
    Given a product repository with products
    When the user retrieves the product list
    Then the product list is successfully retrieved