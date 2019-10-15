Feature: As an user, I would like to re-roll dices that I don't want

  Scenario: User re-roll once and keep 0 dices
    Given user roll dice
    And user hold dices:
      |  |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User re-roll all dices once
    Given user roll dice
    Then wait for 1
    And user reroll dices

    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 1 dice
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario:  User keep 5 dices
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 0 dices and keep 0 dices and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      |  |
    And user hold dices:
      |  |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 0 dices and keep 1 dices and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      |  |

    Then wait for 1
    And user hold dices:
      | 0 |

    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 0 dices and keep 5 dices and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      |  |

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 0 dices and re-roll all and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      |  |

    Then wait for 1
    And user reroll dices
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 1 dices and keep 0 and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |
    Then wait for 1
    And user hold dices:
      |  |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 1 dices and keep 1 and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |
    Then wait for 1
    And user hold dices:
      | 0 |

    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 1 dices and keep 5 and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |

    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 1 dices and re-roll all and score
    Given user roll dice
    Then wait for 1
    And user hold dices:
      | 0 |
    Then wait for 1
    And user reroll dices
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 5 dices and keep 0 and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |

    Then wait for 1
    And user hold dices:
      |  |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 5 dices and keep 1 and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |

    Then wait for 1
    And user hold dices:
      | 0 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |


  Scenario: User keep 5 dices and keep 5 and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User keep 5 dices and re-roll all and score
    Given user roll dice

    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then wait for 1
    And user reroll dices
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User re-roll all and keep 0 and score
    Given user roll dice
    Then wait for 1
    And user reroll dices

    Then wait for 1
    And user hold dices:
      |  |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User re-roll all and keep 1 and score
    Given user roll dice
    Then wait for 1
    And user reroll dices

    Then wait for 1
    And user hold dices:
      | 0 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |

  Scenario: User re-roll all and keep 5 and score
    Given user roll dice
    Then wait for 1
    And user reroll dices


    Then wait for 1
    And user hold dices:
      | 0 |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |


  Scenario: User re-roll all and re-roll all and score
    Given user roll dice
    Then wait for 1
    And user reroll dices
    Then wait for 1
    And user reroll dices
    Then user score to 1
    Then wait for 1
    Then category score is correct
    Then set section to default:
      | aces |
