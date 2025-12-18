Feature: Login functionality

  As a user
  I want to login to SauceDemo
  So that I can see the inventory page

  @smoke
  Scenario: Successful login with valid credentials
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "secret_sauce"
    Then inventory page should be displayed

  @smoke
  Scenario: Invalid credentials show error
    Given user is on SauceDemo login page
    When user logs in with username "standard_user" and password "wrong_password"
    Then error message should be "Epic sadface: Username and password do not match any user in this service"

  @regression
  Scenario: Locked out user cannot login
    Given user is on SauceDemo login page
    When user logs in with username "locked_out_user" and password "secret_sauce"
    Then error message should be "Epic sadface: Sorry, this user has been locked out."
