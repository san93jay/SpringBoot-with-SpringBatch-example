Feature: Account Batch File
  ### GET method with Positive scenario
 Scenario: Get Account details when search by account number or Customer Id or Description
        Given  Send Account details with updated value
        When   I send a put request to the URL "/update" to update Account details
        Then   update given account with updated value and receive status code as 200

