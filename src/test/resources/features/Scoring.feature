Feature: Scoring

  Scenario: User does not get any Ace and want to score on Aces
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    And user score to 1
    Then wait for 1
    Then scoreboard section is 1 :
      | aces |
    Then set section to default:
      | aces |
    Then wait for 1

  Scenario: User get one Ace and want to score on Aces
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    And user score to 1
    Then wait for 1
    Then scoreboard section is 0 :
      | aces |
    Then set section to default:
      | aces |
    Then wait for 1


  Scenario: User get five Aces and want to score on Aces
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 1 |
      | 1 |
      | 1 |
    And user score to 1
    Then wait for 1
    Then scoreboard section is 5 :
      | aces |
