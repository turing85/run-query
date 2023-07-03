Feature: I can run queries

  Scenario: I can run a query
    Given A clean state
    When I run the query "SELECT 1 = 1;"
    Then The query succeeds

  Scenario: I can create and delete a table
    Given A clean state
    And Table "foo" does not exist
    When I run the query "CREATE TABLE foo( id SERIAL, name VARCHAR(255) );"
    And  I run the query "DROP TABLE foo;"
    Then All queries succeed

  Scenario: I cannot create the same table twice
    Given A clean state
    And Table "foo" does not exist
    When I run the query "CREATE TABLE foo( id SERIAL, name VARCHAR(255) );"
    And  I run the query "CREATE TABLE foo( id SERIAL, name VARCHAR(255) );"
    Then The 1st query succeeds
    And The 2nd query fails