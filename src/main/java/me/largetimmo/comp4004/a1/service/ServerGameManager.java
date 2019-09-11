package me.largetimmo.comp4004.a1.service;


import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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



        return playerBO;
    }
}
