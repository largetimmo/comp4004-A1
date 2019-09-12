package me.largetimmo.comp4004.a1.service;


import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.bo.LowerSectionGameScore;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import me.largetimmo.comp4004.a1.service.bo.UpperSectionGameScore;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ServerGameManager {

    private ServerMessageIOController serverMessageIOController;

    private List<PlayerBO> players;

    public ServerGameManager(ServerMessageIOController serverMessageIOController) {
        this.serverMessageIOController = serverMessageIOController;
        players = new ArrayList<>();
    }

    public PlayerBO initPlayer(Socket connection){
        if (connection.isClosed()){
            log.error("Connection is already closed. Failed to init player. Server needs to restart");
            throw new RuntimeException("Failed to init player.");
        }
        PlayerBO playerBO = new PlayerBO();
        playerBO.setPlayerId(UUID.randomUUID().toString());
        playerBO.setClient(connection);
        ScoreSheetBO scoreSheet = new ScoreSheetBO();
        scoreSheet.setLowerSection(new LowerSectionGameScore());
        scoreSheet.setUpperSection(new UpperSectionGameScore());
        playerBO.setScoreSheet(scoreSheet);

        return playerBO;
    }
}
