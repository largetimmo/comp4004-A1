Feature: As an user, I want my dices can convert to points correctly

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

  Scenario: User get 0 points on upper section and does not get bonus
    Given user roll dice
    And user has dices:
      | 6 |
      | 6 |
      | 6 |
      | 6 |
      | 6 |
    And user score to 5
    Then wait for 1
    Then scoreboard section is 0 :
      | upperTotal |
    Then set section to default:
      | fives |

  Scenario: User get 62 points on upper section and does not get bonus
    Given user roll dice
    And put 20 on :
      | fives |
    And put 12 on :
      | threes |
    And user has dices:
      | 6 |
      | 6 |
      | 6 |
      | 6 |
      | 6 |
    And user score to 6
    Then wait for 1
    Then scoreboard section is 62 :
      | upperTotal |
    Then set section to default:
      | sixes  |
      | fives  |
      | threes |

  Scenario: User get 63 points on upper section and  get bonus
    Given user roll dice
    And put 20 on :
      | fives |
    And put 12 on :
      | threes |
    And put 1 on :
      | aces |
    And user has dices:
      | 6 |
      | 6 |
      | 6 |
      | 6 |
      | 6 |
    And user score to 6
    Then wait for 1
    Then scoreboard section is 98 :
      | upperTotal |
    Then set section to default:
      | sixes  |
      | fives  |
      | threes |
      | aces   |

  Scenario: User get two of a kind and want to score on three of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    And user score to 7
    Then wait for 1
    Then scoreboard section is 0 :
      | threeOfAKind |
    Then set section to default:
      | threeOfAKind |

  Scenario: User get three of a kind and want to score on three of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 4 |
      | 5 |
    And user score to 7
    Then wait for 1
    Then scoreboard section is 15 :
      | threeOfAKind |
    Then set section to default:
      | threeOfAKind |

  Scenario: User get four of a kind and want to score on three of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 5 |
    And user score to 7
    Then wait for 1
    Then scoreboard section is 13 :
      | threeOfAKind |
    Then set section to default:
      | threeOfAKind |

  Scenario: User get five of a kind and want to score on three of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 7
    Then wait for 1
    Then scoreboard section is 10 :
      | threeOfAKind |
    Then set section to default:
      | threeOfAKind |

  Scenario: User get three of a kind and want to score on four of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 4 |
      | 5 |
    And user score to 8
    Then wait for 1
    Then scoreboard section is 0 :
      | fourOfAKind |
    Then set section to default:
      | fourOfAKind |

  Scenario: User get four of a kind and want to score on four of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 5 |
    And user score to 8
    Then wait for 1
    Then scoreboard section is 13 :
      | fourOfAKind |
    Then set section to default:
      | fourOfAKind |

  Scenario: User get five of a kind and want to score on three of a kind
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 2 |
      | 2 |
      | 2 |
    And user score to 8
    Then wait for 1
    Then scoreboard section is 10 :
      | fourOfAKind |
    Then set section to default:
      | fourOfAKind |

  Scenario: User get two pairs and want to score on full house
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 3 |
      | 3 |
      | 4 |
    And user score to 9
    Then wait for 1
    Then scoreboard section is 0 :
      | fullHouse |
    Then set section to default:
      | fullHouse |

  Scenario: User get full house and want to score on full house
    Given user roll dice
    And user has dices:
      | 2 |
      | 2 |
      | 3 |
      | 3 |
      | 3 |
    And user score to 9
    Then wait for 1
    Then scoreboard section is 25 :
      | fullHouse |
    Then set section to default:
      | fullHouse |

  Scenario: User get sequence of 3 and want to score on small straight
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 6 |
      | 5 |
      | 4 |
    And user score to 10
    Then wait for 1
    Then scoreboard section is 0 :
      | smallStraight |
    Then set section to default:
      | smallStraight |

  Scenario: User get sequence of 4 and want to score on small straight
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 3 |
      | 6 |
      | 4 |
    And user score to 10
    Then wait for 1
    Then scoreboard section is 30 :
      | smallStraight |
    Then set section to default:
      | smallStraight |

  Scenario: User get sequence of 5 and want to score on small straight
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 3 |
      | 5 |
      | 4 |
    And user score to 10
    Then wait for 1
    Then scoreboard section is 30 :
      | smallStraight |
    Then set section to default:
      | smallStraight |

  Scenario: User get sequence of 4 and want to score on large straight
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 3 |
      | 6 |
      | 4 |
    And user score to 11
    Then wait for 1
    Then scoreboard section is 0 :
      | largeStraight |
    Then set section to default:
      | largeStraight |

  Scenario: User get sequence of 5 and want to score on large straight
    Given user roll dice
    And user has dices:
      | 1 |
      | 2 |
      | 3 |
      | 5 |
      | 4 |
    And user score to 11
    Then wait for 1
    Then scoreboard section is 40 :
      | largeStraight |
    Then set section to default:
      | largeStraight |

  Scenario: User does not get YAHTZEE and want to score on YAHTZEE
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 2 |
      | 1 |
      | 1 |
    And user score to 12
    Then wait for 1
    Then scoreboard section is 0 :
      | Yahtzee |
    Then set section to default:
      | Yahtzee |


  Scenario: User get YAHTZEE and want to score on YAHTZEE
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 1 |
      | 1 |
      | 1 |
    And user score to 12
    Then wait for 1
    Then scoreboard section is 50 :
      | Yahtzee |
    Then reset user

  Scenario: User get YAHTZEE again and want to score on full house
    Given user roll dice
    And user has dices:
      | 1 |
      | 1 |
      | 1 |
      | 1 |
      | 1 |
    And user score to 9
    Then wait for 1
    Then scoreboard section is 1 :
      | YahtzeeBonus |
    Then scoreboard section is 25 :
      | fullHouse |
    And put 0 on :
      | YahtzeeBonus |
    Then set section to default:
      | Yahtzee |



