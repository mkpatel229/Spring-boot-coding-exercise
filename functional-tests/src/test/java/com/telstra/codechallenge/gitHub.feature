Feature: As an api user I want to retrieve some oldest user with zero follower

  Scenario: Get 2 Users with zero follower
    Given url microserviceUrl
    And path 'gitHub/zeroFollowerUser'
    And param per_page = 2
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    * def gitSchema = {"id": '#number',"login": '#string',"html_url": '#string'}
    And match response == {"items": '#[2](gitSchema)'}

  Scenario: Get default(10) number of Users with zero follower
    Given url microserviceUrl
    And path 'gitHub/zeroFollowerUser'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    * def gitSchema = {"id": '#number',"login": '#string',"html_url": '#string'}
    And match response == {"items": '#[10](gitSchema)'}

  Scenario: Get 100 Users with zero follower
    Given url microserviceUrl
    And path 'gitHub/zeroFollowerUser'
    And param per_page = 100
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    * def gitSchema = {"id": '#number',"login": '#string',"html_url": '#string'}
    And match response == {"items": '#[100](gitSchema)'}