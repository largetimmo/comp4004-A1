package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

@Data
public class UpperSectionGameScore {

    private Integer aces = -1;

    private Integer twos = -1;

    private Integer threes = -1;

    private Integer fours = -1;

    private Integer fives = -1;

    private Integer sixes = -1;

    private Integer upperTotal = 0;

    public void calculateTotal(){
        upperTotal = 0;
        if(aces > 0){
            upperTotal += aces;
        }
        if(twos > 0){
            upperTotal += twos;
        }
        if(threes > 0){
            upperTotal += threes;
        }
        if(fours > 0){
            upperTotal += fours;
        }
        if(fives > 0){
            upperTotal += fives;
        }
        if(sixes > 0){
            upperTotal += sixes;
        }
        if(upperTotal >= 63){
            upperTotal += 35;
        }
    }
}
