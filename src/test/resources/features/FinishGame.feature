Feature: As an user, I would like to finish game after all players play 13 rounds

  Scenario: Game can finish after 39 (13*3) rounds
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
    Then user 2 roll dice
    And user 2 score to 2
    Then wait for 1
    Then user 2 finish round 2
    Then user 3 roll dice
    And user 3 score to 2
    Then wait for 1
    Then user 3 finish round 2
    Then user 1 roll dice
    And user 1 score to 3
    Then wait for 1
    Then user 1 finish round 3
    Then user 2 roll dice
    And user 2 score to 3
    Then wait for 1
    Then user 2 finish round 3
    Then user 3 roll dice
    And user 3 score to 3
    Then wait for 1
    Then user 3 finish round 3
    Then user 1 roll dice
    And user 1 score to 4
    Then wait for 1
    Then user 1 finish round 4
    Then user 2 roll dice
    And user 2 score to 4
    Then wait for 1
    Then user 2 finish round 4
    Then user 3 roll dice
    And user 3 score to 4
    Then wait for 1
    Then user 3 finish round 4
    Then user 1 roll dice
    And user 1 score to 5
    Then wait for 1
    Then user 1 finish round 5
    Then user 2 roll dice
    And user 2 score to 5
    Then wait for 1
    Then user 2 finish round 5
    Then user 3 roll dice
    And user 3 score to 5
    Then wait for 1
    Then user 3 finish round 5
    Then user 1 roll dice
    And user 1 score to 6
    Then wait for 1
    Then user 1 finish round 6
    Then user 2 roll dice
    And user 2 score to 6
    Then wait for 1
    Then user 2 finish round 6
    Then user 3 roll dice
    And user 3 score to 6
    Then wait for 1
    Then user 3 finish round 6
    Then user 1 roll dice
    And user 1 score to 7
    Then wait for 1
    Then user 1 finish round 7
    Then user 2 roll dice
    And user 2 score to 7
    Then wait for 1
    Then user 2 finish round 7
    Then user 3 roll dice
    And user 3 score to 7
    Then wait for 1
    Then user 3 finish round 7
    Then user 1 roll dice
    And user 1 score to 8
    Then wait for 1
    Then user 1 finish round 8
    Then user 2 roll dice
    And user 2 score to 8
    Then wait for 1
    Then user 2 finish round 8
    Then user 3 roll dice
    And user 3 score to 8
    Then wait for 1
    Then user 3 finish round 8
    Then user 1 roll dice
    And user 1 score to 9
    Then wait for 1
    Then user 1 finish round 9
    Then user 2 roll dice
    And user 2 score to 9
    Then wait for 1
    Then user 2 finish round 9
    Then user 3 roll dice
    And user 3 score to 9
    Then wait for 1
    Then user 3 finish round 9
    Then user 1 roll dice
    And user 1 score to 10
    Then wait for 1
    Then user 1 finish round 10
    Then user 2 roll dice
    And user 2 score to 10
    Then wait for 1
    Then user 2 finish round 10
    Then user 3 roll dice
    And user 3 score to 10
    Then wait for 1
    Then user 3 finish round 10
    Then user 1 roll dice
    And user 1 score to 11
    Then wait for 1
    Then user 1 finish round 11
    Then user 2 roll dice
    And user 2 score to 11
    Then wait for 1
    Then user 2 finish round 11
    Then user 3 roll dice
    And user 3 score to 11
    Then wait for 1
    Then user 3 finish round 11
    Then user 1 roll dice
    And user 1 score to 12
    Then wait for 1
    Then user 1 finish round 12
    Then user 2 roll dice
    And user 2 score to 12
    Then wait for 1
    Then user 2 finish round 12
    Then user 3 roll dice
    And user 3 score to 12
    Then wait for 1
    Then user 3 finish round 12
    Then user 1 roll dice
    And user 1 score to 13
    Then wait for 1
    Then user 1 finish round 13
    Then user 2 roll dice
    And user 2 score to 13
    Then wait for 1
    Then user 2 finish round 13
    Then user 3 roll dice
    And user 3 score to 13
    Then wait for 1
    Then user 3 finish round 13

    Then wait for 1
    Then game finished
