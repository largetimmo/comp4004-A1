package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

import java.net.Socket;

@Data
public class PlayerBO {

    private String playerId;

    private String playerName;

    private ScoreSheetBO scoreSheet;

    private Connection connection;
}
