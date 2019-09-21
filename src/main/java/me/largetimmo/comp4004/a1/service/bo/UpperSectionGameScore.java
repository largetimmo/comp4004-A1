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

    private Integer total = 0;

    public void calculateTotal(){
        total = 0;
        if(aces > 0){
            total += aces;
        }
        if(twos > 0){
            total += twos;
        }
        if(threes > 0){
            total += threes;
        }
        if(fours > 0){
            total += fours;
        }
        if(fives > 0){
            total += fives;
        }
        if(sixes > 0){
            total += sixes;
        }
        if(total >= 63){
            total += 35;
        }
    }
}
