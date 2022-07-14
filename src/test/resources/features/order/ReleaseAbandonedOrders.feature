Feature: User cancels all orders that have not been paid in a given time period

  Scenario: Cancel abandoned orders works as expected
    Given a lifetime expiration time
    And an order repository with orders
    And a product repository with products
    When the user cancels abandoned orders
    Then the abandoned orders are cancelled