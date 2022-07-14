Feature: User deletes a product

  Scenario: Delete product works as expected
    Given a valid product ID
    And a product repository with products
    When the user deletes the product
    Then the product is successfully deleted

  Scenario: Delete product fails due to an invalid product ID
    Given an invalid product ID
    And a product repository with products
    When the user deletes the product
    Then delete product fails with bad request error

  Scenario: Delete product fails due to an invalid product ID
    Given a non existent product ID
    And a product repository with products
    When the user deletes the product
    Then delete product fails with product not found error