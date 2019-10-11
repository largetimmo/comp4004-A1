package me.largetimmo.comp4004.a1.service.bo;


import lombok.Data;

@Data
public class LowerSectionGameScore {

    private Integer threeOfAKind = -1;

    private Integer fourOfAKind = -1;

    private Integer fullHouse = -1;

    private Integer smallStraight = -1;

    private Integer largeStraight = -1;

    private Integer Yahtzee = -1;

    private Integer chance = -1;

    private Integer YahtzeeBonus = 0;

    private Integer lowerTotal = 0;

    public void  calculateTotal(){
        lowerTotal = 0;
        if(threeOfAKind > 0){
            lowerTotal += threeOfAKind;
        }
        if(fourOfAKind > 0){
            lowerTotal += fourOfAKind;
        }
        if(fullHouse > 0){
            lowerTotal += fullHouse;
        }
        if(smallStraight > 0){
            lowerTotal += smallStraight;
        }
        if(largeStraight > 0){
            lowerTotal += largeStraight;
        }
        if(Yahtzee > 0){
            lowerTotal += Yahtzee;
        }if(YahtzeeBonus > 0){
            lowerTotal += YahtzeeBonus*100;
        }
        if(chance > 0){
            lowerTotal += chance;
        }
    }

}
