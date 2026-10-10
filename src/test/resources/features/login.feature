@login
Feature: Login
  As a shopper I want to log in so that I can check out my order.

  Background:
    Given the app is launched on the catalog
    And I open the login screen

  @smoke
  Scenario: Successful login with a valid user
    When I log in with username "bob@example.com" and password "10203040"
    Then I am taken to the catalog

  Scenario Outline: Login is rejected with a clear message - <case>
    When I log in with username "<username>" and password "<password>"
    Then I see the message "<message>"

    Examples:
      | case               | username          | password | message                                                     |
      | locked out user    | alice@example.com | 10203040 | Sorry, this user has been locked out.                       |
      | unknown user       | 1@2.com           | f-o-o    | Provided credentials do not match any user in this service. |
      | missing username   |                   |          | Username is required                                        |
      | missing password   | bob@example.com   |          | Password is required                                        |
