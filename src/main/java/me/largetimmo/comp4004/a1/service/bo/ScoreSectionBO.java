package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoreSectionBO {

    @Data
    class Round{
        UpperSectionGameScore upper;
        LowerSectionGameScore lower;
        Integer total;
    }

    private List<Round> rounds;

    public ScoreSectionBO() {
        this.rounds = new ArrayList<>();
    }
}
