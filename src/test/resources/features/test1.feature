Feature: Test Extraction
  This feature tests the correct extraction of feature files


  Scenario: Feature files are used in plugin execution
    Given there are 3 feature files in the test/resources/features directory
    When I execute the plugin
    Then they should be included in the execution

  Scenario: A Project has several features
    Given 3 feature files are included in the plugin execution
    When I execute the plugin
    Then they should be converted into JSON-Format and printed into a file

