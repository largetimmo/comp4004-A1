package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

@Data
public class UpperSectionGameScore {

    private Integer aces;

    private Integer twos;

    private Integer threes;

    private Integer fours;

    private Integer fives;

    private Integer sixes;

    private Integer subTotal;

    private Boolean bonus;

    private Integer total;
}
