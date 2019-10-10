Feature: Scoring

  Scenario: User does not get any Ace and want to score on Aces
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

