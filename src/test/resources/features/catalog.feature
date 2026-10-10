@catalog
Feature: Product catalog
  As a shopper I want to browse and sort products so that I can find what I need.

  Background:
    Given the app is launched on the catalog

  Scenario Outline: Sort products by <option>
    When I sort the products by "<option>"
    Then the product <field> are sorted "<direction>"

    Examples:
      | option     | field  | direction  |
      | NAME_ASC   | names  | ascending  |
      | NAME_DESC  | names  | descending |
      | PRICE_ASC  | prices | ascending  |
      | PRICE_DESC | prices | descending |

  @smoke
  Scenario: Open a product from the catalog
    When I open the product "Sauce Labs Backpack"
    Then the product details show "Sauce Labs Backpack" for 29.99
