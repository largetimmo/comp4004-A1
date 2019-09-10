package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

@Data
public class ScoreSheetBO {

    private ScoreSectionBO upperSection;

    private ScoreSectionBO lowerSection;
}
