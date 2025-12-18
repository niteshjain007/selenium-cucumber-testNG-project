Feature: Inventory sorting

  As a user
  I want to sort inventory items
  So that I can view products by name or price order

  @regression
  Scenario Outline: Sort products using inventory sort options
    Given user is on SauceDemo login page
    When user logs in with username "<username>" and password "<password>"
    Then inventory page should be displayed
    And user sorts products by "<sort>"
    Then products should be sorted by "<sort>"

    Examples:
      | username      | password     | sort                  |
      | standard_user | secret_sauce | Name (A to Z)         |
      | standard_user | secret_sauce | Name (Z to A)         |
      | standard_user | secret_sauce | Price (low to high)   |
      | standard_user | secret_sauce | Price (high to low)   |


