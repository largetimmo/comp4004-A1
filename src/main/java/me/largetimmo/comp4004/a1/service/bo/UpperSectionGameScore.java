package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

@Data
public class UpperSectionGameScore {

    private Integer aces = 0;

    private Integer twos = 0;

    private Integer threes = 0;

    private Integer fours = 0;

    private Integer fives = 0;

    private Integer sixes = 0;

    private Integer total = 0;

    public void calculateTotal(){
        total = aces + twos + threes + fours + fives + sixes;
        if(total >= 63){
            total += 35;
        }
    }
}
