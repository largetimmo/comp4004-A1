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
    Then set section to default:
      | aces |

  Scenario: User get 0 Twos and want to score on Two
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 1 |
      | 1 |
      | 1 |
    And user score to 2
    Then wait for 1
    Then scoreboard section is 0 :
      | twos |
    Then set section to default:
      | twos |

  Scenario: User get 1 Twos and want to score on Two
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 2 |
      | 1 |
      | 1 |
    And user score to 2
    Then wait for 1
    Then scoreboard section is 2 :
      | twos |
    Then set section to default:
      | twos |

  Scenario: User get 5 Twos and want to score on Two
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 2
    Then wait for 1
    Then scoreboard section is 10 :
      | twos |
    Then set section to default:
      | twos |

  Scenario: User get 0 Threes and want to score on Three
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 3
    Then wait for 1
    Then scoreboard section is 0 :
      | threes |
    Then set section to default:
      | threes |

  Scenario: User get 1 Threes and want to score on Three
    Given user roll dice
    And user has dices:
      | 3 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 3
    Then wait for 1
    Then scoreboard section is 3 :
      | threes |
    Then set section to default:
      | threes |

  Scenario: User get 5 Threes and want to score on Three
    Given user roll dice
    And user has dices:
      | 3 |
      | 3 |
      | 3 |
      | 3 |
      | 3 |
    And user score to 3
    Then wait for 1
    Then scoreboard section is 15 :
      | threes |
    Then set section to default:
      | threes |

  Scenario: User get 0 Fours and want to score on Four
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 4
    Then wait for 1
    Then scoreboard section is 0 :
      | fours |
    Then set section to default:
      | fours |

  Scenario: User get 1 Fours and want to score on Four
    Given user roll dice
    And user has dices:
      | 4 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 4
    Then wait for 1
    Then scoreboard section is 4 :
      | fours |
    Then set section to default:
      | fours |

  Scenario: User get 5 Fours and want to score on Four
    Given user roll dice
    And user has dices:
      | 4 |
      | 4 |
      | 4 |
      | 4 |
      | 4 |
    And user score to 4
    Then wait for 1
    Then scoreboard section is 20 :
      | fours |
    Then set section to default:
      | fours |

  Scenario: User get 0 Fives and want to score on Five
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 5
    Then wait for 1
    Then scoreboard section is 0 :
      | fives |
    Then set section to default:
      | fives |

  Scenario: User get 1 Fives and want to score on Five
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 5 |
      | 2 |
    And user score to 5
    Then wait for 1
    Then scoreboard section is 5 :
      | fives |
    Then set section to default:
      | fives |

  Scenario: User get 5 Fives and want to score on Five
    Given user roll dice
    And user has dices:
      | 5 |
      | 5 |
      | 5 |
      | 5 |
      | 5 |
    And user score to 5
    Then wait for 1
    Then scoreboard section is 25 :
      | fives |
    Then set section to default:
      | fives |

  Scenario: User get 0 Sixes and want to score on Six
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 6
    Then wait for 1
    Then scoreboard section is 0 :
      | sixes |
    Then set section to default:
      | sixes |

  Scenario: User get 1 Sixes and want to score on Six
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 6 |
      | 2 |
    And user score to 6
    Then wait for 1
    Then scoreboard section is 6 :
      | sixes |
    Then set section to default:
      | sixes |

  Scenario: User get 5 Sixes and want to score on Six
    Given user roll dice
    And user has dices:
      | 6 |
      | 6 |
      | 6 |
      | 6 |
      | 6 |
    And user score to 6
    Then wait for 1
    Then scoreboard section is 30 :
      | sixes |
    Then set section to default:
      | sixes |