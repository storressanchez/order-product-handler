Feature: User requests data about all orders

  Scenario: Get order list works as expected
    Given an order repository with orders
    When the user retrieves the order list
    Then the order list is successfully retrieved