Feature: User requests data about an order

  Scenario: Get order by ID works as expected
    Given a valid order ID
    And an order repository with orders
    When the user retrieves the order
    Then the order is successfully retrieved

  Scenario: Get order by ID fails due to an invalid order ID
    Given an invalid order ID
    And an order repository with orders
    When the user retrieves the order
    Then get order by ID fails with bad request error

  Scenario: Get order by ID fails due to a non existent order ID
    Given a non existent order ID
    And an order repository with orders
    When the user retrieves the order
    Then get order by ID fails with order not found error