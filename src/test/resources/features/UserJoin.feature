Feature: As an user, I would like to join the server to play
# |--------|--|---------|
  # 0 .....2.3.4.........
  Scenario: 0 player cannot start game
    Given server starts
    And wait for player join
    Then 0 player joined server
    Then game is not start

  Scenario: 1 player cannot start game
    Given server starts
    And wait for player join
    Then 1 player joined server
    Then game is not start

  Scenario: 2 player cannot start game
    Given server starts
    And wait for player join
    Then 2 player joined server
    Then game is not start

  Scenario: 3 players shall start game
    Given server starts
    And wait for player join
    Then 3 player joined server
    Then wait for 1
    Then game starts

  Scenario:  4th player cannot join the game
    Given server starts
    And wait for player join
    Then 3 player joined server
    Then wait for 1
    Then game starts
    Then 1 player cannot join server

