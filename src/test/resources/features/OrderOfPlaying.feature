Feature: As an user, I would to play in order and start a round after my previous player finished

  Scenario: all player should play in order and play one by one
    Given user 1 roll dice
    And user 1 score to 1
    Then wait for 1
    Then user 1 finish round 1

    Then user 2 roll dice
    And user 2 score to 1
    Then wait for 1
    Then user 2 finish round 1

    Then user 3 roll dice
    And user 3 score to 1
    Then wait for 1
    Then user 3 finish round 1

    Then user 1 roll dice
    And user 1 score to 2
    Then wait for 1
    Then user 1 finish round 2

    Then set user 1 section to default:
      | aces |
      | twos |
    Then set user 2 section to default:
      | aces |
    Then set user 3 section to default:
      | aces |


