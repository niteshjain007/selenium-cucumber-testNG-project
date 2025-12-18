Feature: Page URL checks

  As a tester
  I want to verify key page URLs
  So that I can demonstrate pass/fail with screenshots on failure

  @smoke
  Scenario: Login page URL is correct
    Given user is on SauceDemo login page
    Then current page URL should be "https://www.saucedemo.com/"

  @regression
  Scenario: Inventory page URL is correct after login
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    Then current page URL should be "https://www.saucedemo.com/inventory.html"

  @regression
  Scenario: Intentional failure - wrong inventory URL to show screenshot
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    # This expected URL is intentionally incorrect to demonstrate failure and screenshot capture
    Then current page URL should be "https://www.saucedemo.com/inventory"

  @regression
  Scenario: Intentional failure - wrong cart URL to show screenshot
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    And user opens the cart page
    # This expected URL is intentionally incorrect to demonstrate failure and screenshot capture
    Then current page URL should be "https://www.saucedemo.com/cart"


