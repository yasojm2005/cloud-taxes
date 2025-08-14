Feature: Tax calculation via API
  As a POS integrator
  I want to calculate taxes using inline definitions and country strategies
  So that totals are correct per business rules

  Scenario: Colombia - Fixed does not enter VAT base
    Given an order payload for country "UY" with 1 item and unit price 100.00
    And the item has inline taxes:
      | type   | name | rate | amountPerUnit |
      | FIXED  | ICO  |      | 5.00          |
      | PERCENT| IVA  | 0.22 |               |
    When I POST it to "/api/taxes/calculate"
    Then the response is 200
    And item "P-UY" totalTax equals 25.90


