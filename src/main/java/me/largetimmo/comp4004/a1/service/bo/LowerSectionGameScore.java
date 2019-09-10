package me.largetimmo.comp4004.a1.service.bo;


import lombok.Data;

@Data
public class LowerSectionGameScore {

    private Integer threeOfAKind;

    private Integer fourOfAKind;

    private Integer fullHouse;

    private Integer smallStraight;

    private Integer largeStraight;

    private Integer Yahtzee;

    private Integer chance;

    private Integer boundCount = 0;

    private Integer YahtzeeBonus = 0;

    private Integer total;

}
