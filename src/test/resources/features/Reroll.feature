Feature: As an user, I would like to re-roll dices that I don't want
  Scenario: User re-roll once and keep 0 dices
    Given user roll dice
    And user hold dices:
      | |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User re-roll all dices once
    Given user roll dice
    And user reroll dices
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

    Scenario: User keep 0 dices and keep 0 dices and score
    Scenario: User keep 0 dices and keep 1 dices and score
    Scenario: User keep 0 dices and keep 5 dices and score
    Scenario: User keep 0 dices and re-roll all and score
