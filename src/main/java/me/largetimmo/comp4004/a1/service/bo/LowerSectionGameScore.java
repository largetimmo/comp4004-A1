package me.largetimmo.comp4004.a1.service.bo;


import lombok.Data;

@Data
public class LowerSectionGameScore {

    private Integer threeOfAKind = 0;

    private Integer fourOfAKind = 0;

    private Integer fullHouse = 0;

    private Integer smallStraight = 0;

    private Integer largeStraight = 0;

    private Integer Yahtzee = 0;

    private Integer chance = 0;

    private Integer YahtzeeBonus = 0;

    private Integer total = 0;

    public void  calculateTotal(){
        total = threeOfAKind + fourOfAKind + fullHouse + smallStraight + largeStraight + Yahtzee + chance + YahtzeeBonus*100;
    }

}
