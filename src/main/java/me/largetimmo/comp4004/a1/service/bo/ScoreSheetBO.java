package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

@Data
public class ScoreSheetBO {

    private UpperSectionGameScore upperSection = new UpperSectionGameScore();

    private LowerSectionGameScore lowerSection = new LowerSectionGameScore();

    private Integer total = 0;

    public void calculateTotal(){
        upperSection.calculateTotal();
        lowerSection.calculateTotal();
        total = upperSection.getUpperTotal() + lowerSection.getLowerTotal();
    }

}
