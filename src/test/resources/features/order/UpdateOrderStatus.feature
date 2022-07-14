Feature: User updates the status of an order

  Scenario: Update order to paid works as expected
    Given a valid order ID
    And a valid status to pay the order
    And an order repository with an order to pay
    And a product repository with products
    When the user updates the order
    Then the order status is successfully updated to paid

  Scenario: Update order to cancelled works as expected
    Given a valid order ID
    And a valid status to cancel the order
    And an order repository with an order to cancel
    And a product repository with products
    When the user updates the order
    Then the order status is successfully updated to cancelled

  Scenario: Update order fails due to an invalid order ID
    Given an invalid order ID
    And a valid status to cancel the order
    And an order repository with orders
    And a product repository with products
    When the user updates the order
    Then update order status fails with bad request error

  Scenario: Update order fails due to an invalid status
    Given a valid order ID
    And an invalid status
    And an order repository with orders
    And a product repository with products
    When the user updates the order
    Then update order status fails with bad request error

  Scenario: Update order fails to a non existent order ID
    Given a non existent order ID
    And a valid status to cancel the order
    And an order repository with orders
    And a product repository with products
    When the user updates the order
    Then update order status fails with order not found error

  Scenario: Update order fails due to a non accepted status
    Given a valid order ID
    And a valid status to cancel the order
    And a non accepted status
    And an order repository with orders
    And a product repository with products
    When the user updates the order
    Then update order status finds the order but fails with bad request error
