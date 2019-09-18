package me.largetimmo.comp4004.a1.configuration.dto;


import lombok.Data;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;

@Data
public class PlayerDTO {

    private String playerId;

    private String playerName;

    private ScoreSheetBO scoreSheet;

}
