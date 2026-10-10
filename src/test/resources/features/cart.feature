@cart
Feature: Shopping cart
  As a shopper I want to manage my cart so that I buy the right items.

  Background:
    Given the app is launched on the catalog

  @smoke
  Scenario: Adding a product updates the cart badge
    When I open the product "Sauce Labs Backpack"
    And I add the product to the cart
    Then the cart badge shows 1

  Scenario Outline: Cart total reflects quantity and unit price - <product>
    When I open the product "<product>"
    And I set the quantity to <quantity>
    And I add the product to the cart
    Then the cart badge shows <quantity>
    When I open the cart
    Then the cart contains the product
    And the cart total matches quantity times unit price

    Examples:
      | product                  | quantity |
      | Sauce Labs Bike Light    | 2        |
      | Sauce Labs Fleece Jacket | 3        |

  Scenario: Removing the only item empties the cart
    When I open the product "Sauce Labs Bolt T-Shirt"
    And I add the product to the cart
    And I open the cart
    And I remove the item from the cart
    Then the cart is empty
