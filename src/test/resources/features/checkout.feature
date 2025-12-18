Feature: Checkout flow

  As a customer
  I want to add a product to cart and checkout
  So that I can purchase items successfully

  @regression
  Scenario Outline: User adds a product and proceeds to checkout overview
    Given user is on SauceDemo login page
    When user logs in with username "<username>" and password "<password>"
    And user adds product "<product>" to the cart from inventory
    And user opens the cart and starts checkout
    And user enters first name "<firstName>" last name "<lastName>" postal code "<postalCode>" and continues
    Then checkout overview shows product "<product>"

    Examples:
      | username       | password      | product               | firstName | lastName | postalCode |
      | standard_user  | secret_sauce  | Sauce Labs Backpack   | John      | Doe      | 12345      |
      | standard_user  | secret_sauce  | Sauce Labs Bike Light | Jane      | Smith    | 90210      |

  @smoke
  Scenario: Validation message appears when mandatory info is missing
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    And user adds product "Sauce Labs Backpack" to the cart from inventory
    And user opens the cart and starts checkout
    And user enters first name "" last name "Doe" postal code "12345" and continues
    Then checkout info error should be "Error: First Name is required"

  @regression
  Scenario: Complete checkout and verify Thank You page
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    And user adds product "Sauce Labs Backpack" to the cart from inventory
    And user adds product "Sauce Labs Bike Light" to the cart from inventory
    And user opens the cart and starts checkout
    And user enters first name "John" last name "Doe" postal code "12345" and continues
    Then checkout overview shows product "Sauce Labs Backpack"
    And checkout overview shows product "Sauce Labs Bike Light"
    And user finishes the checkout
    Then order completion page should be displayed


