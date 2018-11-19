Feature: To search junit in google


@JunitScenario @Search
  Scenario: Junit Google
    Given I am on "cucumber" search page
    When I type "junit"
    Then I click search button
    Then I clear search textbox
    
 @Regression @Search
    Scenario: Cucumber Google
      Given I am on "google" search page
      When I type "cucumber"
      Then I click search button
      Then I clear search textbox