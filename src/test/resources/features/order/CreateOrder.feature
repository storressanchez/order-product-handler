Feature: User creates an order

  Scenario: Create order works as expected but there is not enough stock
    Given an order for which there is not enough product stock
    And an order repository with orders
    And a product repository with products
    When the user creates the order
    Then create order returns the list of missing products

  Scenario: Create order works as expected and there is enough stock
    Given a valid input order
    And an order repository with orders
    And a product repository with products
    When the user creates the order
    Then the order is successfully created

  Scenario: Create order fails due to an invalid input order
    Given an invalid input order
    And an order repository with orders
    And a product repository with products
    When the user creates the order
    Then create order fails with bad request error

  Scenario: Create order fails due to due to a non existent product ID
    Given an order with a non existent product ID
    And an order repository with orders
    And a product repository with products
    When the user creates the order
    Then create order fails with product not found error