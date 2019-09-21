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

    private Integer total = 0;

    public void  calculateTotal(){
        total = 0;
        if(threeOfAKind > 0){
            total += threeOfAKind;
        }
        if(fourOfAKind > 0){
            total += fourOfAKind;
        }
        if(fullHouse > 0){
            total += fullHouse;
        }
        if(smallStraight > 0){
            total += smallStraight;
        }
        if(largeStraight > 0){
            total += largeStraight;
        }
        if(Yahtzee > 0){
            total += Yahtzee;
        }if(YahtzeeBonus > 0){
            total += YahtzeeBonus*100;
        }
        if(chance > 0){
            total += chance;
        }
    }

}
